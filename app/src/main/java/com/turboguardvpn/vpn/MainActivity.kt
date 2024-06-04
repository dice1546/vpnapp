package com.turboguardvpn.vpn

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.turboguardvpn.vpn.data.BottomNavItem
import com.turboguardvpn.vpn.data.DrawerNavItem
import com.turboguardvpn.vpn.ui.screens.agreement.AgreementScreen
import com.turboguardvpn.vpn.ui.screens.connect.ConnectScreen
import com.turboguardvpn.vpn.ui.screens.countries.CountriesScreen
import com.turboguardvpn.vpn.ui.screens.onboarding.OnBoardingScreen
import com.turboguardvpn.vpn.ui.screens.premium.PremiumScreen
import com.turboguardvpn.vpn.ui.screens.setting.SettingsScreen
import com.turboguardvpn.vpn.ui.screens.signin.SignInScreen
import com.turboguardvpn.vpn.ui.theme.TurboGuardVPNVersion01Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
            val hasAgreed = sharedPreferences.getBoolean("hasAgreed", false)
            val startDestination = when {
                isFirstTime && !hasAgreed -> "agreement"
                !isFirstTime && hasAgreed -> "signin"
                else -> "signin"
            }
            if (currentDestination?.route != "onboarding" && currentDestination?.route != "signin" && currentDestination?.route != "agreement") {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 30.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 25.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                ) {
                                    Image(
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Account",
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                                Column(
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                ) {

                                    Text(
                                        text = "Daniyal Afaqi",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 17.sp,
                                        )
                                    )
                                    Text(
                                        text = "Diconnected",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                        )
                                    )

                                }
                            }
                            Divider()
                            Text(
                                text = "Main Menu",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                ),
                                modifier = Modifier.padding(
                                    horizontal = 25.dp,
                                    vertical = 10.dp
                                )
                            )
                            drawerItems.forEach { drawerItem ->

                                NavigationDrawerItem(
                                    label = {
                                        Text(text = drawerItem.title)
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == drawerItem.route } == true,
                                    onClick = {
                                        navController.navigate(drawerItem.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        scope.launch { drawerState.close() }
                                        if (drawerItem.title == "Sign Out") {
                                            viewModel.signOut()
                                            navController.navigate("onboarding") {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    inclusive =
                                                        true // This ensures that all previous destinations are removed from the back stack
                                                }
                                            }
                                        }
                                    },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (drawerItem.badges != 0) {
                                                    Badge {
                                                        Text(text = drawerItem.badges.toString())
                                                    }
                                                } else if (drawerItem.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (currentDestination?.hierarchy?.any { it.route == drawerItem.route } == true) drawerItem.selectedIcon else drawerItem.unselectedIcon,
                                                contentDescription = drawerItem.title
                                            )
                                        }
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp)
                                )
                            }
                        }
                    }

                ) {
                    Scaffold(
                        bottomBar = {
//                                if (currentDestination?.route != "onboarding" && currentDestination?.route != "signin") {
                            NavigationBar {
                                bottomNavItems.forEach { bottomNavItem ->
                                    NavigationBarItem(
                                        selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true,
                                        onClick = {
                                            navController.navigate(bottomNavItem.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }

                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {
                                                    if (bottomNavItem.badges != 0) {
                                                        Badge {
                                                            Text(text = bottomNavItem.badges.toString())
                                                        }
                                                    } else if (bottomNavItem.hasNews) {
                                                        Badge()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = if (currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                                                    contentDescription = bottomNavItem.title
                                                )
                                            }
                                        },
                                        label = {
                                            Text(text = bottomNavItem.title)
                                        }
                                    )
                                }
                            }

                        },
                        floatingActionButton = {
                            if (currentDestination?.route != "premium" && currentDestination?.route != "signin" && currentDestination?.route != "onboarding") {
                                FloatingActionButton(onClick = {
                                    navController.navigate(route = "premium") {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = com.turboguardvpn.vpn.R.drawable.premium),
                                        contentDescription = "premium",
                                        tint = Color(0xFF000000)
                                    )
                                }
                            }

                        },
                        topBar = {
                            bottomNavItems.forEach {
                                if (it.route == currentDestination?.route) {
                                    CenterAlignedTopAppBar(

                                        title = {

                                            Text(text = it.title)

                                        },

                                        navigationIcon = {
                                            IconButton(
                                                onClick = {
                                                    // Open the drawer when the icon is clicked
                                                    scope.launch { drawerState.open() }
                                                },
                                                modifier = Modifier
                                                    .padding(horizontal = 12.dp)
                                                    .background(
                                                        color = Color(0xFFE8E9EA),
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(30.dp),
                                                    imageVector = Icons.Outlined.Menu,
                                                    contentDescription = "Menu"

                                                )
                                            }
                                        },
                                        actions = {
                                            IconButton(
                                                onClick = {
                                                    navController.navigate(route = "premium") {
                                                        popUpTo(navController.graph.findStartDestination().id) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                },
                                                modifier = Modifier
                                                    .padding(horizontal = 12.dp)
                                                    .background(
                                                        color = Color(0xFFE8E9EA),
                                                        shape = RoundedCornerShape(10.dp)
                                                    )
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .size(30.dp),
                                                    painter = painterResource(id = com.turboguardvpn.vpn.R.drawable.premium),
                                                    contentDescription = "Search"
                                                )
                                            }
                                        }
                                    )
                                }
                            }

                        },
                    )
                    { innerPadding ->
                        NavigationGraph(
                            navController, innerPadding,
                            startDestination
                        )
                    }
                }
            } else {
                NavigationGraph(navController, PaddingValues(), startDestination)
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        Modifier.padding(innerPadding)
    ) {
        composable("connect") {
            ConnectScreen(navController)
        }
        composable("agreement") { AgreementScreen(navController) }
        composable("countries") { CountriesScreen(navController) }
        composable("setting") { SettingsScreen(navController) }
        composable("premium") { PremiumScreen(navController) }
        composable("onboarding") {
            OnBoardingScreen(navController)
        }
        composable("signin") {
            SignInScreen(navController)

        }
    }

}


val bottomNavItems = listOf(
    BottomNavItem(
        title = "Countries",
        route = "countries",
        selectedIcon = Icons.Filled.Place,
        unselectedIcon = Icons.Outlined.Place,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "TurboGuard",
        route = "connect",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        badges = 0
    ),
    BottomNavItem(
        title = "Setting",
        route = "setting",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = false,
        badges = 0
    )

)
val drawerItems = listOf(
    DrawerNavItem(
        title = "Setting",
        route = "setting",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = false,
        badges = 0
    ),
    DrawerNavItem(
        title = "Account",
        route = "onboarding",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false,
        badges = 0
    ),
    DrawerNavItem(
        title = "Show Log",
        route = "log",
        selectedIcon = Icons.Filled.Lock,
        unselectedIcon = Icons.Outlined.Lock,
        hasNews = false,
        badges = 0
    ),
    DrawerNavItem(
        title = "Report",
        route = "report",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        hasNews = false,
        badges = 0
    ),
    DrawerNavItem(
        title = "Help",
        route = "help",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        hasNews = true,
        badges = 3
    ),
    DrawerNavItem(
        title = "Sign Out",
        route = "signin",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        hasNews = false,
        badges = 0
    )
)

