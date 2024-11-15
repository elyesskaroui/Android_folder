import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String // Add other fields as needed
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RefreshTokenRequest(
    val refreshToken: String
)

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val newPassword: String,
    val resetToken: String
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,


    // Add other fields as per your API response
)
interface AuthService {

    @POST("auth/signup")
    fun signUp(@Body signupRequest: SignupRequest): Call<Void>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<AuthResponse>

    @POST("auth/refresh")
    fun refreshTokens(@Body refreshTokenRequest: RefreshTokenRequest): Call<AuthResponse>

    @PUT("auth/change-password")
    fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Call<Unit> // Modify the return type as per your response

    @POST("auth/forgot-password")
    fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Call<Unit> // Modify the return type as per your response

    @PUT("auth/reset-password")
    fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Call<Unit> // Modify the return type as per your response
    companion object {
        var BASE_URL = "http://10.0.2.2:3000/"
        fun create(): AuthService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(AuthService::class.java)
        }
    }

}