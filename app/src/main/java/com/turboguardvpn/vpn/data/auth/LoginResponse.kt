package com.turboguardvpn.vpn.data.auth

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)