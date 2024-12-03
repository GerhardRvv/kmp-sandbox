package org.gerhard.kmp_sandbox_demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dependencies.MyViewModel
import kmpsandboxdemo.composeapp.generated.resources.Res
import kmpsandboxdemo.composeapp.generated.resources.ic_battery
import kmpsandboxdemo.composeapp.generated.resources.ic_censor
import kmpsandboxdemo.composeapp.generated.resources.ic_clock
import kmpsandboxdemo.composeapp.generated.resources.ic_counter
import kmpsandboxdemo.composeapp.generated.resources.ic_settings
import networking.InsultCensorClient
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.CensorTextScreen
import ui.CitiesScreen
import ui.CounterScreen
import ui.GreetingScreen
import ui.PermissionsScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
    batteryManager: BatteryManager,
    cameraManager: CameraManager,
    client: InsultCensorClient,
    prefs: DataStore<Preferences>,
    myViewModel: MyViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitleForRoute(currentRoute)) },
                backgroundColor = Color.White,
                contentColor = Color.Black
            )
        },
        bottomBar = {
            Surface(
                color = Color.LightGray,
                elevation = 8.dp
            ) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            batteryManager = batteryManager,
            cameraManager = cameraManager,
            client = client,
            prefs = prefs,
            myViewModel = myViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    batteryManager: BatteryManager,
    client: InsultCensorClient,
    prefs: DataStore<Preferences>,
    myViewModel: MyViewModel,
    modifier: Modifier = Modifier,
    cameraManager: CameraManager,
) {
    NavHost(navController, startDestination = "cities", modifier = modifier) {
        composable("cities") { CitiesScreen() }
        composable("permissions") { PermissionsScreen(cameraManager) }
        composable("counter") { CounterScreen(prefs) }
        composable("greeting") { GreetingScreen(batteryManager, myViewModel) }
        composable("censor") { CensorTextScreen(client) }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val items = listOf(
            "cities" to Res.drawable.ic_clock,
            "permissions" to Res.drawable.ic_settings,
            "counter" to Res.drawable.ic_counter,
            "greeting" to Res.drawable.ic_battery,
            "censor" to Res.drawable.ic_censor
        )
        items.forEach { (route, iconRes) ->
            BottomNavigationItem(
                icon = { Icon(painterResource(resource = iconRes), contentDescription = null) },
                label = { Text(route.capitalize()) },
                selected = currentRoute == route,
                onClick = { navController.navigate(route) }
            )
        }
    }
}

fun screenTitleForRoute(route: String?): String {
    return when (route) {
        "cities" -> "Cities"
        "permissions" -> "Permissions"
        "counter" -> "Counter"
        "greeting" -> "Greeting"
        "censor" -> "Censor"
        else -> "App Navigation"
    }
}


