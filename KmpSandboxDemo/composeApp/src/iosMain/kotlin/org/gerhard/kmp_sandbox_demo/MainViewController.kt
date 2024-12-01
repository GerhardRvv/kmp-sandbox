package org.gerhard.kmp_sandbox_demo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import createDataStore
import di.initKoin
import io.ktor.client.engine.darwin.Darwin
import networking.InsultCensorClient
import networking.createHttpClient

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
        client = remember {
            InsultCensorClient(
                httpClient = createHttpClient(Darwin.create())
            )
        },
        batteryManager = BatteryManager(),
        prefs = createDataStore()
    )
}