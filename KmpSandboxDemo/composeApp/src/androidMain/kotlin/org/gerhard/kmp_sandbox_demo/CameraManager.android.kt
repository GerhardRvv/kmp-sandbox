package org.gerhard.kmp_sandbox_demo

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

actual class CameraManager(
    private val activity: Activity
) {
    actual fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    companion object {
        const val CAMERA_REQUEST_CODE = 100
    }
}