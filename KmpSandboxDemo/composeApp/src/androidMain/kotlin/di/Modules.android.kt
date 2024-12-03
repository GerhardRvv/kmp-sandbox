package di

import dependencies.DbClient
import dependencies.MyViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.gerhard.kmp_sandbox_demo.CameraManager

actual val platformModule = module {
    singleOf(::DbClient)
    viewModelOf(::MyViewModel)
    single { CameraManager(get()) }
}