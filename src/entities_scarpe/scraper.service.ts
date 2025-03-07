import { Injectable, Logger } from '@nestjs/common';
import axios from 'axios';
import * as puppeteer from 'puppeteer';
import { GoogleGenerativeAI } from '@google/generative-ai';

@Injectable()
export class ScraperService {
  private readonly logger = new Logger(ScraperService.name);

  private browserConfig = {
    headless: true,
  };

  private urls: string[] = [
   "https://www.vidal.fr/",
    "https://www.boiron.com/who-are-we",
    "https://www.who.int",
    "https://pubmed.ncbi.nlm.nih.gov",
    "https://www.mayoclinic.org",
    "https://www.uptodate.com",
    "https://www.cochranelibrary.com",
    "https://www.nih.gov",
    "https://pubmed.ncbi.nlm.nih.gov/clinical/",
  ];


  private apiKey = "AIzaSyAF8BIDiyO4lsLEVofbfYZkGSyULQjfkfA"; 

  private schema = {
    "paragraph": "str",
  };

  // Initialisation correcte de Gemini API
  private genAI = new GoogleGenerativeAI(this.apiKey);
  private model = this.genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

  async scrapeAllSites(info :string): Promise<any[]> {
    this.logger.log('🔍 Scraping started for multiple sites...');

    if (!this.urls || this.urls.length === 0) {
      this.logger.error('⚠ No URLs found to scrape.');
      return [];
    }

    const results = await Promise.all(
      this.urls.map(async (url) => {
        this.logger.log(`🌐 Scraping URL: ${url}`);

        try {
          const result = await this.scrapeSite(url,info);
          this.logger.log(`✅ Scraping Succeeded for ${url}`);
          return {
           
            data: result?.extractedData || null,
          };
        } catch (error) {
          this.logger.error(`❌ Scraping Failed for ${url}: ${error.message}`);
          return {
            url,
            success: false,
            error: error.message,
          };
        }
      })
    );

    return results;
  }

  async scrapeSite(url: string,info : string): Promise<{ extractedData: any, usageInfo: any }> {
    const browser = await puppeteer.launch(this.browserConfig);

    try {
      const page = await browser.newPage();
      await page.goto(url, { waitUntil: 'networkidle2', timeout: 60000 });

      const html = await page.content();
     // console.log(`📜 HTML content of ${url}:`, html);

      const extractedData = await this.extractDataWithGemini(html,info);

      const usageInfo = {
        totalTokens: Math.floor(Math.random() * 5000) + 1000,
        promptTokens: Math.floor(Math.random() * 3000) + 500,
        completionTokens: Math.floor(Math.random() * 2000) + 500,
        totalCost: (Math.random() * 0.05).toFixed(4),
      };

      return { extractedData, usageInfo };
    } catch (error) {
      this.logger.error(`❌ Error scraping ${url}: ${error.message}`);
      throw error;
    } finally {
      await browser.close();
    }
  }

  private async extractDataWithGemini(html: string,info : string): Promise<any[]> {
    try {
      console.log(info);
      //const info = "Information_médicale"; // Remplacez ceci par l'information médicale spécifique à rechercher

      // const instruction = `Extract all medical information from paragraphs in the HTML. 
      // Search specifically for this information: ' "${info}" '. 
      // Return a JSON response with the format: 
      // {
      //   "found": true/false, 
      //   "data": "extracted data if found", 
      //   "source": "website URL containing the information"
      // }. 
      // Focus only on the provided information. 
      // If found, return the related data and the website that contains it with {"found": true}. 
      // If not found, return {"found": false}.`;

      const instruction = `Analyze the given HTML content and extract all relevant medical information. 
        Compare this information against the provided statement: "${info}". 
        Return a JSON response with the following format:

        {
          "found": true/false,
          "match": true/false, 
          "data": "extracted data if found", 
          "source": "website URL containing the information"
          
        }

        - "found": true if relevant data is found, false otherwise. and it must be about the given statment 
        - "match": true if the extracted data confirms that "${info}" is correct, false if it contradicts it.
        - "data": The extracted relevant information.
        - "source": The URL containing the extracted information.

        Ensure the response explicitly states whether the extracted data supports or contradicts the provided information.`;

      
      const prompt = `${instruction}\n\n${html}`;
      
      // Envoi de la requête à Gemini
      const result = await this.model.generateContent(prompt);
      const responseText = result.response.text();

      if (!responseText) {
        this.logger.error('⚠ Empty response from Gemini API');
        return [];
      }
      try {
        let cleanText = responseText.trim();
    
        // Remove code block indicators like ```json at the start and ```
        cleanText = cleanText.replace(/^```json\s*/, '').replace(/```$/, '').trim();
    
        let parsed = JSON.parse(cleanText);
        console.log(parsed);
        
        return Array.isArray(parsed) ? parsed.filter(item => item.paragraph) :parsed ;
    } catch (parseError) {
        this.logger.error('❌ Failed to parse Gemini response as JSON: ' + responseText);
        return [];
    }
    
    
    } catch (error) {
      this.logger.error(`❌ Gemini API Error: ${error.message}`);
      throw new Error(`Gemini extraction failed: ${error.message}`);
    }
  }



  /* URL VERIFY API */
  async analyzeImage(imageUrl: string): Promise<{ found: boolean; related: boolean; details: string }> {
    try {
        this.logger.log(`🔍 Analyzing image: ${imageUrl}`);

        // 1. Fetch the image as binary data
        const imageResp = await fetch(imageUrl);
        const imageBuffer = await imageResp.arrayBuffer();

        // 2. Prepare the prompt with image and instructions
        const instruction = `first describe the image 
            Analyze this image and determine if it relates to health or medicine.
            Focus on elements like medical instruments, hospitals, medicines, healthcare workers, biological diagrams, etc.
            Your response must be JSON in this exact format:
            {     "description": you must describe the provided image give it description ,
                "found": true/false,
                "related": true/false,
                "details": "Explanation of why it is or isn't health-related"
            }
        `;

        // 3. Send image + prompt to Gemini
        const result = await this.model.generateContent([
            {
                inlineData: {
                    data: Buffer.from(imageBuffer).toString('base64'),
                    mimeType: 'image/jpeg', // adapt if needed
                },
            },
            instruction,
        ]);

        const responseText = result.response.text();
        this.logger.log(`✅ Gemini response: ${responseText}`);

        // 4. Parse Gemini's response into expected format
        const analysis = JSON.parse(responseText);

        // 5. Return structured result
        return {
            found: analysis.found ?? false,
            related: analysis.related ?? false,
            details: analysis.details ?? 'No details provided',
        };

    } catch (error) {
        this.logger.error(`❌ Error analyzing image: ${error.message}`);
        return {
            found: false,
            related: false,
            details: 'Failed to analyze image',
        };
    }
}
/******************************* */


  async analyzeVideo(videoUrl: string): Promise<{ found: boolean; related: boolean; details: string }> {
    try {
      this.logger.log(`🔍 Analyzing video: ${videoUrl}`);

      const instruction = `
        Analyze the given video and determine if it is related to health or medicine. 
        Consider elements like medical procedures, healthcare workers, hospitals, medicines, biological illustrations, etc.
        
        Return a JSON response with the format:
        {
          "found": true/false,
          "related": true/false,
          "details": "Explanation of why it is or isn't health-related"
        }.
      `;

      const prompt = `${instruction}\n\nVideo URL: ${videoUrl}`;

      // Call an external AI service to analyze the video content
      const result = await this.model.generateContent(prompt);
      const responseText = result.response.text();

      if (!responseText) {
        this.logger.error('⚠ Empty response from AI model');
        return { found: false, related: false, details: 'No valid response received.' };
      }

      let cleanText = responseText.trim();
      cleanText = cleanText.replace(/^```json\s*/, '').replace(/```$/, '').trim();

      let parsed;
      try {
        parsed = JSON.parse(cleanText);
        return parsed;
      } catch (parseError) {
        this.logger.error('❌ Failed to parse AI response as JSON');
        return { found: false, related: false, details: 'Failed to process AI response.' };
      }

    } catch (error) {
      this.logger.error(`❌ Video analysis failed: ${error.message}`);
      throw new Error(`Video analysis failed: ${error.message}`);
    }
  }

}
