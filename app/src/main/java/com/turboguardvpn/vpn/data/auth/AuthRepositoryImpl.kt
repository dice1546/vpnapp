package com.turboguardvpn.vpn.data.auth

import android.content.SharedPreferences
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository {




    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password,
                )
            )
            signIn(username, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            val loginResponse = response.body()

            if (loginResponse != null) {
                prefs.edit().putString("accessToken", loginResponse.data.accessToken).apply()
                prefs.edit().putString("refreshToken", loginResponse.data.refreshToken).apply()
                Log.d("AuthRepositoryImpl", "Access token: ${loginResponse.data.accessToken}")
                Log.d("AuthRepositoryImpl", "Access token: ${loginResponse.data.refreshToken}")
            }

            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }


    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("accessToken", null)
            if (token.isNullOrEmpty()) {
                Log.d("AuthRepositoryImpl", "Token is null or empty, unauthorized access.")
                return AuthResult.Unauthorized()
            }
            Log.d("AuthRepositoryImpl", "Token is valid, attempting authentication.")
            api.authenticate("Bearer $token")
            Log.d("AuthRepositoryImpl", "Authentication successful.")
            AuthResult.Authorized()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Exception occurred: ${e.message}", e)
            AuthResult.Unauthorized()
        }
    }

    override suspend fun signOut(): AuthResult<Unit> {
        return try {
            // Call the API logout function
            api.logout()
            val editor = prefs.edit()
            editor.remove("accessToken")
            editor.remove("refreshToken")
            editor.apply()

            AuthResult.Unauthorized()
        } catch (e: HttpException) {
            // Handle HttpException
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        }
    }


}