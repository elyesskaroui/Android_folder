package com.example.jetpackcomposeauthui.ui

import AuthResponse
import LoginRequest
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Create an instance of AuthService
    val authService = AuthService.create()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
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
                    text = "Sign In",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                )

                Text(
                    "Please log in to access the details and results of the security tests.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF38587E)
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                )

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
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("home")

                        coroutineScope.launch {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                val loginRequest = LoginRequest(email, password)

                                authService.login(loginRequest).enqueue(object : Callback<AuthResponse> {

                                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                                        coroutineScope.launch {
                                            if (response.isSuccessful) {
                                                val authResponse = response.body()
                                                if (authResponse != null) {
                                                    Log.d("LoginScreen", "Access Token: ${authResponse.accessToken}")
                                                    snackbarHostState.showSnackbar("Logged in successfully!")
                                                    navController.navigate("home")
                                                } else {

                                                    snackbarHostState.showSnackbar("Unexpected error, please try again.")

                                                }
                                            } else {

                                                snackbarHostState.showSnackbar("Invalid credentials, please try again.")
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                                        coroutineScope.launch {
                                            Log.e("LoginScreen", "Login failed: ${t.message}")
                                            snackbarHostState.showSnackbar("Login failed: ${t.message}")
                                        }
                                    }
                                })
                            } else {
                                snackbarHostState.showSnackbar("Please enter your credentials")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Login", color = Color.White)
                }


                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .clickable {
                            navController.navigate("forgot_password")
                        }
                        .align(Alignment.CenterHorizontally),
                    color = Color(0xFF186ACB)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Or sign in with",
                    color = Color(0xFF1362C0),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google1),
                        contentDescription = "Gmail",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 16.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Facebook",
                        modifier = Modifier.size(40.dp)
                    )
                }

                DontHaveAccountRow(onSignupTap = {
                    navController.navigate("signup")
                })
            }
        }
    }
}

@Composable
fun DontHaveAccountRow(onSignupTap: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Don't have an account? ",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Blue
            )
        )
        TextButton(onClick = onSignupTap) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
