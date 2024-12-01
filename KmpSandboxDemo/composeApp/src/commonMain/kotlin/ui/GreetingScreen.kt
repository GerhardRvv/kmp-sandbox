package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dependencies.MyViewModel
import kmpsandboxdemo.composeapp.generated.resources.Res
import kmpsandboxdemo.composeapp.generated.resources.compose_multiplatform
import kmpsandboxdemo.composeapp.generated.resources.hello
import org.gerhard.kmp_sandbox_demo.BatteryManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GreetingScreen(batteryManager: BatteryManager, viewModel: MyViewModel) {
    var showContent by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
    }

    AnimatedVisibility(showContent) {
        val greeting = remember { batteryManager.getBatteryLevel().toString() }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Compose: $greeting")
            Text(stringResource(Res.string.hello))
            Text(text = viewModel.getHello())
        }
    }
}
