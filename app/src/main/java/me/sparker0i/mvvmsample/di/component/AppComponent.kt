package me.sparker0i.mvvmsample.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import me.sparker0i.mvvmsample.application.MvvmApplication
import me.sparker0i.mvvmsample.di.module.AppModule
import me.sparker0i.mvvmsample.di.module.BuildersModule
import me.sparker0i.mvvmsample.di.module.NetModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class , NetModule::class])
interface AppComponent {
    fun inject(app: MvvmApplication)
}