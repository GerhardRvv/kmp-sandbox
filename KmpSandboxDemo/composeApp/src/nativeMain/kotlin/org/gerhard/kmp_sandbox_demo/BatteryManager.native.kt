package org.gerhard.kmp_sandbox_demo

actual class BatteryManager {
    actual fun getBatteryLevel(): Int {
        UiDevice.currentDevice.batteryMonitorEnabled = true
        val batteryLevel = UiDevice.currentDevice.batteryLevel

        return (batteryLevel * 100).roundToInt()

    }
}