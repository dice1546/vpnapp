package com.turboguardvpn.vpn.data.auth

data class Data(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)