package com.turboguardvpn.vpn.ui.screens.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.turboguardvpn.vpn.MainViewModel
import com.turboguardvpn.vpn.R
import com.turboguardvpn.vpn.data.auth.AuthResult
import com.turboguardvpn.vpn.data.auth.AuthUiEvent


@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(viewModel, context){
        viewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    navController.navigate("connect") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
//                    Toast.makeText(
//                        context,
//                        "Incorrect Password or Username",
//                        Toast.LENGTH_LONG
//                    ).show()

                }
                is AuthResult.UnknownError -> {
//                    Toast.makeText(
//                        context,
//                        "An Unknown Error Occurred",
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }
        }
    }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val charLimit = 20
    val errorMessage by viewModel.errorMessage

    fun validate(text: String) {
        isError = text.length > charLimit
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.earth),
                contentDescription = "Sample Image",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(130.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Welcome Back!",
                modifier = Modifier.padding(horizontal = 30.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold

                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Welcome Back, we miss you!",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium

                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Gray,
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(15.dp),
                value = state.signInUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))
                    validate(it)
                },
                singleLine = true,
                label = { Text(if (isError) "Email*" else "Email") },
                supportingText = {
                    Row {
//                    Text(if (isError) errorMessage else "", Modifier.clearAndSetSemantics {})
                        Spacer(Modifier.weight(1f))
                        Text("Limit: ${state.signInUsername.length}/$charLimit")
                    }
                },
                isError = isError,
                keyboardActions = KeyboardActions { validate(state.signInUsername) },
                modifier = Modifier.semantics {
                    // Provide localized description of the error
                    if (isError) errorMessage?.let { error(it) }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color(0xFFFFFFFF),
                    unfocusedContainerColor = Color.White,
                    unfocusedTextColor = Color.Gray,
                    focusedIndicatorColor = Color.Blue,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTrailingIconColor = Color.Blue,
                    unfocusedTrailingIconColor = Color.Gray,
                ),
                shape = RoundedCornerShape(15.dp),
                value = state.signInPassword,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
                },
                singleLine = true,
                label = { Text("Enter password") },
                visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) painterResource(id = R.drawable.baseline_remove_red_eye_24) else painterResource(
                                id = R.drawable.baseline_visibility_off_24
                            )
                        // Please provide localized description for accessibility services
                        val description = if (passwordHidden) "Show password" else "Hide password"
                        Icon(painter = visibilityIcon, contentDescription = description)
                    }
                }
            )
            Text(
                text = "Forgot Password?",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 25.dp),
            )

            Button(
                onClick = {
                    viewModel.onEvent(AuthUiEvent.SignIn)
                    viewModel.updateIsFirstTime(false)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 8.dp)
                    .height(50.dp),
                colors = ButtonColors(
                    containerColor = Color.Green,
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.LightGray
                ),
            ) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    ),
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp)
            )
            Button(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 8.dp)
                    .height(50.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFFE8E9EA),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.LightGray
                ),
            ) {
                Text(
                    text = "Sign In with Google",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Don't have an account yet?",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                ),
                modifier = Modifier.padding()
            )
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = Color.Red, style = TextStyle(fontSize = 16.sp))
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()
    SignInScreen(navController = navController)
}

//    val errorMessage = "Text input too long"
//    var text by rememberSaveable { mutableStateOf("") }
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    val errorMessage by signInViewModel.errorMessage
//    val user by signInViewModel.user