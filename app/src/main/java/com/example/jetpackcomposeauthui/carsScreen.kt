package com.example.jetpackcomposeauthui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Image d'arrière-plan
            Image(
                painter = painterResource(id = R.drawable.bg2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.3f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(paddingValues)
            ) {
                // Icône de profil (redimensionnée)
                Image(
                    painter = painterResource(id = R.drawable.ic_cars1), // Remplacez par l'icône de votre profil
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .size(80.dp) // Taille réduite
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Titre
                Text(
                    text = "ajouter un Véhicule",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Champ Matricule (en lecture seule sans icône)
                ProfileField(
                    label = "Matricule",
                    leadingIcon = null, // Icône supprimée ici
                    textColor = Color.Black // Couleur du texte définie sur noir
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Champ Type (en lecture seule sans icône)
                ProfileField(
                    label = "Type",
                    leadingIcon = null, // Icône supprimée ici
                    textColor = Color.Black // Couleur du texte définie sur noir
                )

                // Champ Numéro de châssis (en lecture seule sans icône)
                ProfileField(
                    label = "Numéro de châssis",
                    leadingIcon = null, // Icône supprimée ici
                    textColor = Color.Black // Couleur du texte définie sur noir
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Bouton de déconnexion avec icône
                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Icône pour se déconnecter
                        Icon(
                            imageVector = Icons.Filled.ExitToApp, // Vous pouvez choisir une autre icône ici
                            contentDescription = "Ajouter",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Ajouter un espace entre l'icône et le texte
                        Text(text = "Ajouter", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileField(
    label: String,
    leadingIcon: Any?,
    textColor: Color = Color.Gray // Couleur de texte par défaut sur gris, mais personnalisable
) {
    OutlinedTextField(
        value = "",  // Valeur supprimée du champ
        onValueChange = {},
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp, // Taille uniforme pour tous les labels
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        },
        leadingIcon = {
            // Vérifie si l'icône est non nulle avant de la rendre
            leadingIcon?.let {
                when (it) {
                    is androidx.compose.ui.graphics.vector.ImageVector -> Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Redimensionner l'icône
                    )
                    is androidx.compose.ui.graphics.painter.Painter -> Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp) // Redimensionner l'icône
                    )
                }
            }
        },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = textColor, fontWeight = FontWeight.Medium), // Utilise la couleur passée
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = Color.Gray,
            disabledTextColor = textColor, // Définit la couleur du texte pour l'état désactivé
            disabledLabelColor = Color.Gray
        )
    )
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun ProfileScreenPreview() {
    CarsScreen(navController = rememberNavController())
}
