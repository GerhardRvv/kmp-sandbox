package di

import dependencies.MyRepository
import dependencies.MyRepositoryImpl
import dependencies.MyViewModel
import dev.icerock.moko.permissions.PermissionsController
import org.gerhard.kmp_sandbox_demo.CameraManager
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import viewmodel.PermissionsViewModel

expect val platformModule: Module

val sharedModule = module {
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    viewModelOf(::MyViewModel)


    factory { (permissionsController: PermissionsController, cameraManager: CameraManager) ->
        PermissionsViewModel(permissionsController, cameraManager)
    }
    // Provide PermissionsViewModel
    viewModel { PermissionsViewModel(get(), get()) }
}