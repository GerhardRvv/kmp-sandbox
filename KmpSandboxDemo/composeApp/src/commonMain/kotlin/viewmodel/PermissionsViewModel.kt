package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch
import org.gerhard.kmp_sandbox_demo.CameraManager

class PermissionsViewModel(
    private val controller: PermissionsController,
    private val cameraManager: CameraManager
): ViewModel() {


    var audioPermissionState by mutableStateOf(PermissionState.NotDetermined)
        private set

    var contactsPermissionState by mutableStateOf(PermissionState.NotDetermined)
        private set

    var cameraPermissionState by mutableStateOf(PermissionState.NotDetermined)
        private set

    init {
        viewModelScope.launch {
            audioPermissionState = controller.getPermissionState(Permission.RECORD_AUDIO)
            contactsPermissionState = controller.getPermissionState(Permission.CONTACTS)
            cameraPermissionState = controller.getPermissionState(Permission.CAMERA)
        }
    }

    fun providerOrRequestRecordAudioPermission() {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.RECORD_AUDIO)
                audioPermissionState = PermissionState.Granted
            } catch (e: DeniedAlwaysException) {
                audioPermissionState = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                audioPermissionState = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun providerOrRequestContactsPermission() {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.CONTACTS)
                contactsPermissionState = PermissionState.Granted
            } catch (e: DeniedAlwaysException) {
                contactsPermissionState = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                contactsPermissionState = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun providerOrRequestCameraPermission() {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.CAMERA)
                cameraPermissionState = PermissionState.Granted
                openCamera()
            } catch (e: DeniedAlwaysException) {
                cameraPermissionState = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                cameraPermissionState = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun openCamera() {
        cameraManager.openCamera()
    }
}