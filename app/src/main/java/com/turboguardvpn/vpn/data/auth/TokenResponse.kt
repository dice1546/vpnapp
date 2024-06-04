package com.turboguardvpn.vpn.data.auth

import com.turboguardvpn.vpn.data.User

data class TokenResponse(
    val statusCode: Int,
    val data: TokenData,
    val message: String,
    val success: Boolean
)

data class TokenData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)