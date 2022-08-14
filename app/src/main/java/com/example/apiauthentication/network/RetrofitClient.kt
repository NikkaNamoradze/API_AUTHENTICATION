package com.example.apiauthentication.network

import com.example.apiauthentication.model.login.LoginResponse
import com.example.apiauthentication.model.login.LoginSend
import com.example.apiauthentication.model.register.RegisterResponse
import com.example.apiauthentication.model.register.RegisterSend
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {

    private const val BASE_URL = "https://reqres.in/api/"

    private val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofitBuilder.create(AuthService::class.java)
    }

}

interface AuthService {

    @POST("login")
    suspend fun logIn(@Body body: LoginSend): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body body: RegisterSend): Response<RegisterResponse>
}

