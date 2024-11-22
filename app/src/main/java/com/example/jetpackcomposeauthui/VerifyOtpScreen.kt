import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
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
fun VerifyOtpScreen(
    navController: NavHostController
) {
    // State for each OTP field
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var otp5 by remember { mutableStateOf("") }
    var otp6 by remember { mutableStateOf("") }

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
                    .graphicsLayer(alpha = 0.3f)
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
                    text = AnnotatedString(
                        "Verify OTP",
                        spanStyle = SpanStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Red
                        )
                    )
                )

                Text(
                    text = AnnotatedString(
                        "Enter the OTP sent to your email.",
                        spanStyle = SpanStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF38587E)
                        )
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 24.dp)
                )

                // OTP Fields - 6 individual TextFields
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 6 TextFields for OTP input
                    listOf(otp1, otp2, otp3, otp4, otp5, otp6).forEachIndexed { index, value ->
                        TextField(
                            value = value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1) {
                                    when (index) {
                                        0 -> otp1 = newValue
                                        1 -> otp2 = newValue
                                        2 -> otp3 = newValue
                                        3 -> otp4 = newValue
                                        4 -> otp5 = newValue
                                        5 -> otp6 = newValue
                                    }
                                }
                            },
                            label = null,
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .border(1.dp, Color.Black)
                            ,
                            // Small width to keep the fields compact
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                focusedTextColor = Color.Black, // La couleur du texte lorsque le champ est focusé
                                unfocusedTextColor = Color.Black, // La couleur du texte lorsque le champ n'est pas focusé

                                focusedIndicatorColor = Color.Black,
                                unfocusedIndicatorColor = Color.Black,
                                containerColor = Color.Transparent // Transparent background
                            )
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val otp = "$otp1$otp2$otp3$otp4$otp5$otp6"
                            if (otp.length == 6) {
                                snackbarHostState.showSnackbar("OTP Verified Successfully!")
                                navController.navigate("home") // Replace with the appropriate destination
                            } else {
                                snackbarHostState.showSnackbar("Invalid OTP. Please try again.")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Verify OTP", color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = AnnotatedString(
                            "Didn't receive the OTP? ",
                            spanStyle = SpanStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF156BD0)
                            )
                        )
                    )
                    TextButton(onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Resending OTP...")
                        }
                    }) {
                        Text(
                            text = AnnotatedString(
                                "Resend",
                                spanStyle = SpanStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
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
fun VerifyOtpScreenPreview() {
    VerifyOtpScreen(navController = rememberNavController())
}
