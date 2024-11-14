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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }

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
                    text = "Forgot Password",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                )

                Text(
                    "Enter your email to receive reset instructions.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF38587E)
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                )

                // Email Field with black text color
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email Address", color = Color.Black) }, // Label color set to black
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black, // Text color set to black
                        focusedLabelColor = Color.Black, // Focused label color set to black
                        unfocusedLabelColor = Color.Black // Unfocused label color set to black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            Log.i("ForgotPassword", "Email: $email")

                            if (email.isNotEmpty()) {
                                snackbarHostState.showSnackbar("Reset link sent to your email.")
                            } else {
                                snackbarHostState.showSnackbar("Please enter your email address.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Reset Password", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Remember your password? ",
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
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}
