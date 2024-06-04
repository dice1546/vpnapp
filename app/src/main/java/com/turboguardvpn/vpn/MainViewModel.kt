package com.turboguardvpn.vpn

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turboguardvpn.vpn.data.auth.AuthRepository
import com.turboguardvpn.vpn.data.auth.AuthResult
import com.turboguardvpn.vpn.data.auth.AuthState
import com.turboguardvpn.vpn.data.auth.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    var hasAgreed: MutableState<Boolean> = mutableStateOf(false)
        private set

    var isFirstTime: MutableState<Boolean> = mutableStateOf(true)
        private set

    var getSevenDaysTrial: MutableState<Boolean> = mutableStateOf(false)
        private set

    init {
        authenticate()
    }

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    fun updateIsFirstTime(value: Boolean) {
        isFirstTime.value = value
        sharedPreferences.edit().putBoolean("isFirstTime", value).apply()
    }

    // Function to update getSevenDaysTrial
    fun updateGetSevenDaysTrial(value: Boolean) {
        getSevenDaysTrial.value = value
    }

    fun updateHasAgreed(value: Boolean) {
        hasAgreed.value = value
        sharedPreferences.edit().putBoolean("hasAgreed", value).apply()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
            }

            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }

            is AuthUiEvent.SignIn -> {
                signIn()
            }

            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(signUpUsername = event.value)

            }

            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signInPassword = event.value)

            }

            is AuthUiEvent.SignUp -> {
                signUp()
            }

            is AuthUiEvent.SignOut -> {
                signOut()
            }
        }
    }


    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                username = state.signUpUsername,
                password = state.signUpPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                username = state.signInUsername,
                password = state.signInPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }


    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signOut()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}