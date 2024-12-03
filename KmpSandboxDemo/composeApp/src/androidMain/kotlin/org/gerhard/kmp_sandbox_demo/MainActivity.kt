package org.gerhard.kmp_sandbox_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import createDataStore
import io.ktor.client.engine.okhttp.OkHttp
import networking.InsultCensorClient
import networking.createHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember {
                    InsultCensorClient(
                        httpClient = createHttpClient(OkHttp.create())
                    )
                },
                batteryManager = remember {
                    BatteryManager(applicationContext)
                },
                cameraManager = remember {
                    CameraManager(this)
                },
                prefs = remember {
                    createDataStore(applicationContext)
                }
            )
        }
    }
}
