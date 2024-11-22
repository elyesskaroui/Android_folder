package com.example.jetpackcomposeauthui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.ui.ProfileScreen

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(navController = rememberNavController())
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedScreen by remember { mutableStateOf("Home") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Image de fond avec superposition
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Superposition sombre pour améliorer la lisibilité
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Superposition sombre avec opacité
        )

        Box(modifier = Modifier.fillMaxSize()) {
            // Arrière-plan pour la page
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAF9F6)) // Fond clair professionnel
                    .padding(bottom = 90.dp) // Espace pour la barre de navigation
            ) {
                // Titre de la page Home
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, start = 24.dp)
                ) {
                    Text(
                        text = "Welcome to the Home Page",
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD50000) // Couleur rouge pour le titre
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Ajouter une icône qui représente l'écran Home
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally),
                        tint = Color(0xFFD50000) // Icône rouge
                    )

                    // Ajouter le bouton "Your Cars"
                    Spacer(modifier = Modifier.height(30.dp)) // Espacement avant le bouton
                    Button(
                        onClick = { /* Action pour le bouton Your Cars */ },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.6f) // Largeur de 60% de l'écran
                            .padding(vertical = 200.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD50000)), // Rouge vif
                        shape = RoundedCornerShape(50.dp) // Bordures arrondies
                    ) {
                        Text(
                            text = "Your Cars",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White // Texte en blanc
                            )
                        )
                    }

                }

                // Affichage du contenu pour chaque écran sélectionné
                when (selectedScreen) {
                    "Profile" -> Profilecars(navController)
                    "Add" -> CarsScreen(navController)
                    "Camera" -> Text("Camera Screen", modifier = Modifier.align(Alignment.Center))
                    else -> Text("", modifier = Modifier.align(Alignment.Center))
                }
            }

            // Barre de navigation fixe avec des icônes arrondies et une ombre
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFDEDEDE))
                    .clip(RoundedCornerShape(50.dp))
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
                    .shadow(10.dp, shape = RoundedCornerShape(50.dp), clip = true), // Ombre pour un effet de profondeur
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BottomNavIcon(
                    icon = Icons.Default.Person,
                    contentDescription = "Profile",
                    isSelected = selectedScreen == "Profile",
                    onClick = { selectedScreen = "Profile" },
                    iconSize = 30.dp // Icône plus grande
                )
                BottomNavIcon(
                    drawableId = R.drawable.camera, // L'image située dans le dossier drawable
                    contentDescription = "Camera",
                    isSelected = selectedScreen == "Camera",
                    onClick = { selectedScreen = "Camera" },
                    iconSize = 30.dp // Icône plus grande
                )

                BottomNavIcon(
                    drawableId = R.drawable.car, // L'image située dans le dossier drawable
                    contentDescription = "Add",
                    isSelected = selectedScreen == "Add",
                    onClick = { selectedScreen = "Add" },
                    iconSize = 40.dp, // Taille de l'image
                    alwaysBlue = true
                )
            }
        }
    }
}

@Composable
fun BottomNavIcon(
    drawableId: Int? = null, // Paramètre pour l'image drawable
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null, // Paramètre pour l'icône vectorielle
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSize: androidx.compose.ui.unit.Dp = 30.dp,
    alwaysBlue: Boolean = false
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(iconSize + 20.dp) // Espace total réservé à l'icône
            .background(
                if (isSelected) Color(0xFFD50000).copy(alpha = 0.2f) else Color.Transparent, // Couleur verte lorsqu'elle est sélectionnée
                shape = RoundedCornerShape(50)
            )
    ) {
        if (drawableId != null) {
            // Afficher l'image depuis drawable
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize) // Taille de l'image exacte
            )
        } else if (icon != null) {
            // Afficher l'icône vectorielle
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize), // Taille de l'icône exacte
                tint = if (alwaysBlue || isSelected) Color(0xFFD50000) else Color.Black // Icône verte si sélectionnée
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
