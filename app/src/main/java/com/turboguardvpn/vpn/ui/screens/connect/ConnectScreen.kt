package com.turboguardvpn.vpn.ui.screens.connect

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.turboguardvpn.vpn.R
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun ConnectScreen(
    navController: NavController
) {

    var connectionStatus by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }

    // Simulate connection status change for demo purposes
    LaunchedEffect(Unit) {
        delay(2000) // Delay to simulate connection change
        connectionStatus = true
        while (true) {
            delay(1000L)
            elapsedTime += 1L
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val hours = TimeUnit.SECONDS.toHours(elapsedTime)
        val minutes = TimeUnit.SECONDS.toMinutes(elapsedTime) % 60
        val seconds = elapsedTime % 60
        if (connectionStatus) {
            Text(
                text = "Connecting Time",
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                )
            )
            Text(
                text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 6.dp),
                color = Color.Black,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 12.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .zIndex(6f)

            ) {
                val painter =
                    rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/daniyaldev1546.appspot.com/o/US%20-%20United%20States.png?alt=media&token=bb14e1ca-bf5b-405a-a99e-34a384007065")
                Image(
                    painter = painter,
                    contentDescription = "Remote Sample Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(6.dp)
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(15))
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "United States",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(1.dp)
                    )
                    Text(
                        text = "Texas",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(1.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Stealth:",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(1.dp)
                    )
                    Text(
                        text = "14%",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier.padding(1.dp)
                    )
                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 12.dp)
            ) {
                // First Column
                Column(
                    modifier = Modifier
                        .weight(1f) // Ensures equal spacing between columns
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_downloading_24),
                            contentDescription = "Download Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(2.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "Download",
                                color = Color.Black,
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp,
                                ),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = "527 MB",
                                color = Color.Black,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                ),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
                // Spacer to separate columns
                Spacer(modifier = Modifier.width(6.dp))
                // Second Column
                Column(
                    modifier = Modifier
                        .padding(1.dp)
                        .weight(1f) // Ensures equal spacing between columns
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_cloud_upload_24),
                            contentDescription = "Upload Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(2.dp)
                        )
                        Column(
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "Upload",
                                color = Color.Black,
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 11.sp,
                                ),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = "49 MB",
                                color = Color.Black,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                ),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
            /*
            var progress by remember { mutableFloatStateOf(0.0f) }
            LaunchedEffect(Unit) {
                while (progress < 1f) {
                    delay(50)
                    progress += 0.01f
                }
            }
            */
            // Earth Image
            Image(
                painter = painterResource(id = R.drawable.earth),
                contentDescription = "Earth",
                modifier = Modifier
                    .size(270.dp)
                    .clip(CircleShape)
            )
            /* TODO: Add a Progress Bar around the Earth Image
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .size(200.dp),
                color = Color.Red,
                strokeWidth = 2.dp,

            )*/
            Button(
                onClick = {
                    navController.navigate("setting")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFFE8E9EA),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.globe),
                        contentDescription = "Icon 1",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black,
                    )
                    Text(
                        "Change Server",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 34.dp)
                    )
                    Icon(
                        imageVector = Icons.Outlined.ArrowForward,
                        contentDescription = "Icon 2",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
            }


        } else {
            Column(
                modifier = Modifier
                    .padding(1.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
                // Ensures equal spacing between columns
            ) {
                Text(
                    text = "Status",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    )
                )
                Text(
                    text = "Connecting...",
                    color = Color.Red,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.height(26.dp))
//                val imageUrl = "https://firebasestorage.googleapis.com/v0/b/daniyaldev1546.appspot.com/o/US%20-%20United%20States.png?alt=media&token=bb14e1ca-bf5b-405a-a99e-34a384007065"
//                val painter: Painter = rememberAsyncImagePainter(imageUrl)
                Image(
                    painter = painterResource(id = R.drawable.unitedstates),
                    contentDescription = "Remote Sample Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                )
                Text(
                    text = "United States",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = "Texas",
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(47.dp))
                Image(
                    painter = painterResource(id = R.drawable.earth),
                    contentDescription = "Earth",
                    modifier = Modifier
                        .size(270.dp)
                        .clip(CircleShape)
                )
                Button(
                    onClick = {
                        navController.navigate("setting")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    colors = ButtonColors(
                        containerColor = Color(0xFFE8E9EA),
                        contentColor = Color.Black,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Cancel Connection",
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                        ),
                    )
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ConnectScreenPreview() {
    val navController = rememberNavController()
    ConnectScreen(navController = navController)

}