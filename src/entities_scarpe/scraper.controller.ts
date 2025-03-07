import { Controller, Get, Query } from '@nestjs/common';

import { ScraperService } from './scraper.service';

@Controller('scraper')
export class ScraperController {
  constructor(private readonly scraperService: ScraperService) {}

  @Get('start')
  async startScraping(@Query('info') info: string) {
    const results = await this.scraperService.scrapeAllSites(info);
    // Filter results to get only successful scrapes with found=true
    const validResults = results.filter(result => 
      result.data && result.data.found === true
    );
    
    return {
      success: true,
      foundResults: validResults.length > 0,
      results: validResults,
      message: validResults.length > 0 
        ? 'Information verified and found reliable.' 
        : 'Information could not be verified in trusted medical sources.'
    };
  }


  @Get('analyzeimage')
  async analyzeImage(@Query('imageUrl') imageUrl: string) {
    if (!imageUrl) {
      return {
        success: false,
        message: "No image URL provided",
      };
    }

    const result = await this.scraperService.analyzeImage(imageUrl);

    return {
      success: true,
      analysis: result,
      message: result.related
        ? "The image is related to health."
        : "The image is NOT related to health.",
    };
  }

  // // POST method to upload video file
  // @Post('upload')
  // @UseInterceptors(FileInterceptor('file'))
  // async uploadVideo(@UploadedFile() file: Express.Multer.File) {
  //   try {
  //     // Save file to a local directory (optional)
  //     const filePath = path.join(__dirname, 'uploads', file.originalname);
  //     fs.writeFileSync(filePath, file.buffer);

  //     // Process the uploaded video
  //     const result = await this.analyzeVideo(filePath);

  //     return {
  //       success: true,
  //       message: 'Video uploaded and analyzed successfully.',
  //       analysis: result,
  //     };
  //   } catch (error) {
  //     return {
  //       success: false,
  //       message: 'Error during video upload or analysis.',
  //       error: error.message,
  //     };
  //   }
  // }

  @Get('analyzeVideo')
  async analyzeVideo(@Query('videoUrl') videoUrl: string) {
    if (!videoUrl) {
      return {
        success: false,
        message: 'No video URL provided',
      };
    }

    const result = await this.scraperService.analyzeVideo(videoUrl);

    return {
      success: true,
      analysis: result,
      message: result.related
        ? 'The video is related to health.'
        : 'The video is NOT related to health.',
    };
  }



}
//function Post(arg0: string): (target: ScraperController, propertyKey: "uploadVideo", descriptor: TypedPropertyDescriptor<(file: Express.Multer.File) => Promise<{ success: boolean; message: string; analysis: { success: boolean; message: string; analysis?: undefined; } | { ...; }; error?: undefined; } | { ...; }>>) => void | TypedPropertyDescriptor<...> {
 // throw new Error('Function not implemented.');
//}