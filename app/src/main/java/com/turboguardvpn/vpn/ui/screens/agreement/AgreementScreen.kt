package com.turboguardvpn.vpn.ui.screens.agreement

import android.app.Activity
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.turboguardvpn.vpn.ui.screens.onboarding.OnBoardingScreen

@Composable
fun AgreementScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val hasUserAgreed = viewModel.hasAgreed.value
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                (context as? Activity)?.finish()
            },
            modifier = Modifier.align(Alignment.Start).background(color = Color(0xFFE8E9EA),
                shape = RoundedCornerShape(10.dp)
            )
        ) {
            Icon(imageVector = Icons.Outlined.Close, contentDescription = "quit")
        }

        Image(
            painter = painterResource(id = R.drawable.earth),
            contentDescription = "Sample Image",
            modifier = Modifier
                .padding(top = 20.dp)
                .size(100.dp)
        )

        Text(
            text = "TurboGuard VPN",
            modifier = Modifier.padding(horizontal = 40.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold

            ),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .height(500.dp)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "We strictly follow no-logs policy and important/relevant data privacy laws, and collect (but not retain) the minimum anonymous data such as system country and language for setting up the service.",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal

                ),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    navController.navigate("onBoarding")
                    viewModel.updateHasAgreed(true)
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
                    text = "Agree & Continue",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    ),
                )
            }
            Text(
                text = "For more information, please read our Terms of Service and Privacy Policy",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 25.dp),
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal

                ),
                textAlign = TextAlign.Center
            )
        }

    }


}
