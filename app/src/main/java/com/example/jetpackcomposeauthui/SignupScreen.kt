package com.example.jetpackcomposeauthui.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController
) {
    var userName by remember { mutableStateOf("") } // New state for User Name
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.bg2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.5f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 54.dp)
                        .height(130.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                )

                Text(
                    "Create an account to access the platform.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF38587E)
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                )

                // User Name Field
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text(text = "User Name", color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email Address", color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password", color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Confirm Password", color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            Log.i("credential", "User Name: $userName Email: $email Password: $password Confirm: $confirmPassword")

                            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                                snackbarHostState.showSnackbar("Account created successfully!")
                            } else {
                                snackbarHostState.showSnackbar("Please fill in all fields correctly.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF156BD0)
                        )
                    )
                    TextButton(onClick = {
                        navController.navigate("login")
                    }) {
                        Text(
                            text = "Sign In",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}
