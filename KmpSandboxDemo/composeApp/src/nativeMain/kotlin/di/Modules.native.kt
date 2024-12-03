package di

import dependencies.DbClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.gerhard.kmp_sandbox_demo.CameraManager

actual val platformModule = module {
    singleOf(::DbClient)
    single { CameraManager() }
}