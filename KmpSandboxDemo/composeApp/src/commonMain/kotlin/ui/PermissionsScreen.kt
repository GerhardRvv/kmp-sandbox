package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import org.gerhard.kmp_sandbox_demo.CameraManager
import viewmodel.PermissionsViewModel

@Composable
fun PermissionsScreen(cameraManager: CameraManager) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)
    val permissionsModel = viewModel { PermissionsViewModel(controller, cameraManager) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (permissionsModel.audioPermissionState) {
            PermissionState.DeniedAlways -> {
                Text("Permission denied forever")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            PermissionState.Granted -> {
                Text("Audio Permission granted")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            else -> Button(onClick = permissionsModel::providerOrRequestRecordAudioPermission) {
                Text("Request Audio permission")
            }
        }

        when (permissionsModel.contactsPermissionState) {
            PermissionState.DeniedAlways -> {
                Text("Contacts permission denied forever")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            PermissionState.Granted -> {
                Text("Contacts Permission granted")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            else -> Button(onClick = permissionsModel::providerOrRequestContactsPermission) {
                Text("Request Contacts permission")
            }
        }

        when (permissionsModel.cameraPermissionState) {
            PermissionState.DeniedAlways -> {
                Text("Camera permission denied forever")
                Button(onClick = { controller.openAppSettings() }) {
                    Text("Open App settings")
                }
            }

            PermissionState.Granted -> {
                Text("Camera Permission granted")
                Button(onClick = permissionsModel::openCamera ) {
                    Text("Open Camera")
                }
            }

            else -> Button(onClick = permissionsModel::providerOrRequestCameraPermission ) {
                Text("Request Camera permission")
            }
        }
    }
}
