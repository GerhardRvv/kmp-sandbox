package org.gerhard.kmp_sandbox_demo

import platform.Foundation.NSLog
import platform.UIKit.UIApplication
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerSourceType

actual class CameraManager {
    actual fun openCamera() {
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            ?: throw Exception("Root view controller not found")

        if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)) {
            val picker = UIImagePickerController().apply {
                sourceType =
                    UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            }
            viewController.presentViewController(picker, animated = true, completion = null)
        } else  {
            NSLog("Camera not available")
        }
    }
}