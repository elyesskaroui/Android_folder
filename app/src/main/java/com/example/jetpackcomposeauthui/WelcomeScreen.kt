package com.example.jetpackcomposeauthui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.ui.theme.AlegreyaFontFamily
import com.example.jetpackcomposeauthui.ui.theme.AlegreyaSansFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val isTextVisible = remember { androidx.compose.runtime.mutableStateOf(false) }

    // Lancer l'animation de fondu après un délai
    scope.launch {
        delay(500) // Délai de 500 ms avant de montrer le texte
        isTextVisible.value = true
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Image de fond
        Image(
            painter = painterResource(id = R.drawable.welcome1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

           // .graphicsLayer(alpha = 0.6f) // Réglage de l'opacité
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top, // Modifier pour placer les éléments en haut

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 300.dp),  // Ajout d'un padding en haut de 200dp
                contentScale = ContentScale.Fit
            )

            // Texte "WELCOME" avec animation de fondu
            AnimatedVisibility(
                visible = isTextVisible.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    "WELCOME",
                    fontSize = 34.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                "Ensure safety, reliability, \n and performance.",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontFamily = AlegreyaSansFontFamily,
                fontWeight = FontWeight.Black,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bouton en rouge avec largeur et coins arrondis pour un look professionnel
            Button(
                onClick = {
                    navController.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red // Bouton rouge pour correspondre au thème
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Bouton avec largeur de 80%
                    .height(50.dp), // Hauteur plus grande pour plus de présence
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp) // Coins arrondis
            ) {
                Text(
                    text = "Sign In With Email",
                    color = Color.White, // Texte en blanc pour contraste
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Lien "Don't have an account? Sign up" en gris-bleu pour harmonie avec le design
            DontHaveAccountRow(
                onSignupTap = {
                    navController.navigate("signup")
                }
            )
        }
    }
}

@Composable
fun DontHaveAccountRow(onSignupTap: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = buildAnnotatedString {
                append("Don't have an account? ")  // Texte gris-bleu
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("Sign up")  // Mot "Sign up" en rouge
                }
            },
            color = Color(0xFF38587E), // Gris-bleu pour "Don't have an account?"
            modifier = Modifier.clickable(onClick = onSignupTap),
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(rememberNavController())
}
