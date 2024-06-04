package com.turboguardvpn.vpn.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.turboguardvpn.vpn.MainViewModel
import com.turboguardvpn.vpn.R

@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.earth),
            contentDescription = "Sample Image",
            modifier = Modifier.padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Protected Browsing with No Limits",
            modifier = Modifier.padding( horizontal = 40.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold

            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Experience True Online Freedom: Secure, Private, and Fast VPN",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 25.dp),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium

            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                     // navController.navigate("createaccount")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .height(50.dp),
            colors = ButtonColors(
                containerColor = Color.Green,
                contentColor = Color.Black,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.Gray
            ),
        ) {
            Text(
                text = "Create Account",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                ),
            )
        }
        Button(
            onClick = {
                navController.navigate("signin")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 8.dp)
                .height(50.dp),
            colors = ButtonColors(
                containerColor = Color.Transparent,
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
    }

}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    val navController = rememberNavController()
    OnBoardingScreen(navController = navController)
}