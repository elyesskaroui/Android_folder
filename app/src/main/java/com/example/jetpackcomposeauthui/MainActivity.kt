package com.example.jetpackcomposeauthui

import VerifyOtpScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeauthui.ui.ForgotPasswordScreen
import com.example.jetpackcomposeauthui.ui.LoginScreen
import com.example.jetpackcomposeauthui.ui.ProfileScreen
import com.example.jetpackcomposeauthui.ui.SignUpScreen
//import com.example.jetpackcomposeauthui.ui.VerifyOtpScreen
import com.example.jetpackcomposeauthui.ui.theme.JetpackComposeAuthUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAuthUITheme {
                // Add navigation so users can go from one screen to another
                NavigationView()
            }
        }
    }
}

@Composable
fun NavigationView() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("verify_otp") { VerifyOtpScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile_screen") { Profilecars(navController) }
        composable("edit_profile") {
            EditProfileScreen(
                onBackPressed = { navController.popBackStack() }
            )
    }
}}

