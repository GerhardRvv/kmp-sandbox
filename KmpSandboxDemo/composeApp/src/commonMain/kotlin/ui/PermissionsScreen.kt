package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import viewmodel.PermissionsViewModel

@Composable
fun PermissionsScreen() {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)
    val permissionsModel = viewModel { PermissionsViewModel(controller) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (permissionsModel.state) {
            PermissionState.DeniedAlways -> {
                Text("Permission denied forever")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            PermissionState.Granted -> {
                Text("Permission granted")
            }

            else -> Button(onClick = permissionsModel::providerOrRequestRecordAudioPermission) {
                Text("Request permission")
            }
        }
    }
}
