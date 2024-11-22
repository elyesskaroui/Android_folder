package com.example.jetpackcomposeauthui.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
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
fun ProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
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
                // Profile Icon (resized)
                Image(
                    painter = painterResource(id = R.drawable.ic_profil1), // Replace with your profile icon
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .size(80.dp) // Reduced size
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Your Profile",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Name Field (read-only)
                ProfileField(
                    label = "Name",
                    leadingIcon = Icons.Filled.Person
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field (read-only)
                ProfileField(
                    label = "Email",
                    leadingIcon = Icons.Filled.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Vehicle Matricule (read-only)


                Spacer(modifier = Modifier.height(16.dp))

                // Vehicle Type (read-only)

                Spacer(modifier = Modifier.height(32.dp))

                // Sign Out Button with Icon
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
                        // Icon for Sign Out
                        Icon(
                            imageVector = Icons.Filled.ExitToApp, // You can choose any other icon here
                            contentDescription = "Sign Out",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Add some space between icon and text
                        Text(text = "Sign Out", color = Color.White)
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
    leadingIcon: Any
) {
    var isFocused by remember { mutableStateOf(false) } // Track focus state

    OutlinedTextField(
        value = "",  // Removed value from field
        onValueChange = {},
        label = {
            // Animate the label when focus state changes
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp, // Uniform size for all labels
                    fontWeight = FontWeight.Normal,
                    color = if (isFocused) Color.Blue else Color.Gray // Change color on focus
                ),
                modifier = Modifier.animateContentSize(tween(300)) // Smooth animation
            )
        },
        leadingIcon = {
            when (leadingIcon) {
                is androidx.compose.ui.graphics.vector.ImageVector -> Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp) // Redimensionner l'icône
                )
                is androidx.compose.ui.graphics.painter.Painter -> Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp) // Redimensionner l'icône
                )
            }
        },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused // Update focus state
            },
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Medium),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black,
            disabledLabelColor = Color.Gray
        )
    )
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
