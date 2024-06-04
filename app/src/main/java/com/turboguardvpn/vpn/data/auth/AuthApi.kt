package com.turboguardvpn.vpn.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )
    @POST("login")
    suspend fun signIn(
        @Body request: AuthRequest
    ): Response<TokenResponse>

    @POST("logout")
    suspend fun logout()

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") accessToken: String
    ): Response<Unit>
}

