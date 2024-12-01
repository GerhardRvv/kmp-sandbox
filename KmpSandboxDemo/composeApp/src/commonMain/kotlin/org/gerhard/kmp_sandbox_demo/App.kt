package org.gerhard.kmp_sandbox_demo

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
    client: InsultCensorClient,
    prefs: DataStore<Preferences>,
    viewModel: MyViewModel = koinViewModel()
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Navigation") },
                backgroundColor = Color.White,
                contentColor = Color.Black
            )
        },
        bottomBar = {
            Surface(
                color = Color.LightGray,
                elevation = 8.dp
            ) {
                BottomNavigationBar(navController)
            }
        }
    ) {
        NavigationHost(
            navController = navController,
            batteryManager = batteryManager,
            client = client,
            prefs = prefs,
            viewModel = viewModel
        )
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    batteryManager: BatteryManager,
    client: InsultCensorClient,
    prefs: DataStore<Preferences>,
    viewModel: MyViewModel
) {
    NavHost(navController, startDestination = "cities") {
        composable("cities") { CitiesScreen() }
        composable("permissions") { PermissionsScreen() }
        composable("counter") { CounterScreen(prefs) }
        composable("greeting") { GreetingScreen(batteryManager, viewModel) }
        composable("censor") { CensorTextScreen(client) }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(painterResource(resource = Res.drawable.ic_clock), contentDescription = null) },
            label = { Text("Cities") },
            selected = false,
            onClick = { navController.navigate("cities") }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(resource = Res.drawable.ic_settings), contentDescription = null) },
            label = { Text("Permissions") },
            selected = false,
            onClick = { navController.navigate("permissions") }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(resource = Res.drawable.ic_counter), contentDescription = null) },
            label = { Text("Counter") },
            selected = false,
            onClick = { navController.navigate("counter") }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(resource = Res.drawable.ic_battery), contentDescription = null) },
            label = { Text("Greeting") },
            selected = false,
            onClick = { navController.navigate("greeting") }
        )
        BottomNavigationItem(
            icon = { Icon(painterResource(resource = Res.drawable.ic_censor), contentDescription = null) },
            label = { Text("Censor") },
            selected = false,
            onClick = { navController.navigate("censor") }
        )
    }
}

