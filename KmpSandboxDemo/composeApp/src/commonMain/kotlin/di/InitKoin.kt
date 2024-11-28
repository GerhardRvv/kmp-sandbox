package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) = run {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}