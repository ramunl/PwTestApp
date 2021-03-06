package ru.pwtest.pwapp.di


import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.pwtest.common.scope.PerApplication
import ru.pwtest.dataLayer.di.module.NetworkModule
import ru.pwtest.domain.di.module.RepoModule
import ru.pwtest.pwapp.App
import ru.pwtest.pwapp.di.module.*

@PerApplication
@Component(
    modules = [AppModule::class,
        RepoModule::class,
        AppBuilderModule::class,
        AndroidSupportInjectionModule::class,
        RxModule::class,
        NetworkModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}