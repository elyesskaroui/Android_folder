package com.example.jetpackcomposeauthui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditProfileScreen(
                onBackPressed = { finish() } // Action de retour
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(onBackPressed: () -> Unit) {
    var firstName by remember { mutableStateOf("Benjamin") }
    var lastName by remember { mutableStateOf("Jack") }
    var email by remember { mutableStateOf("benjamin.Jack@gmail.com") }
    var phone by remember { mutableStateOf("+100******00") }
    var showMessage by remember { mutableStateOf("") } // Message à afficher

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Icône personnalisée
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() }) { data ->
                Snackbar(snackbarData = data)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Image de profil
            ProfileImage(painter = painterResource(id = R.drawable.ic_launcher_foreground))

            Spacer(modifier = Modifier.height(16.dp))

            // Nom et champs de formulaire
            Text(text = "Karoui Elyess", fontSize = 20.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            ProfileEditText(label = "First Name", value = firstName) { firstName = it }
            ProfileEditText(label = "Last Name", value = lastName) { lastName = it }
            ProfileEditText(label = "Email", value = email) { email = it }
            ProfileEditText(label = "Phone", value = phone) { phone = it }

            Spacer(modifier = Modifier.height(32.dp))

            // Bouton "Save Changes"
            Button(
                onClick = {
                    // Vérification des champs
                    showMessage = if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && phone.isNotBlank()) {
                        "Save successful"
                    } else {
                        "All fields must be filled"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Save Changes", color = Color.White)
            }

            // Affichage du message
            if (showMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Snackbar(
                    action = {},
                    modifier = Modifier.padding(16.dp),
                    containerColor = if (showMessage == "Save successful") Color.Green else Color.Red
                ) {
                    Text(text = showMessage, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProfileImage(painter: Painter) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Image(painter = painter, contentDescription = "Profile Image")
    }
}

@Composable
fun ProfileEditText(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = value, // Bind the state to the text field
            onValueChange = { newText ->
                onValueChange(newText) // Mise à jour dynamique
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray.copy(alpha = 0.1f), CircleShape)
                .padding(16.dp),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(onBackPressed = {})
}
