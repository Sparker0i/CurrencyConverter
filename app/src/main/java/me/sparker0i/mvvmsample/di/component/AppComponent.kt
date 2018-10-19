package me.sparker0i.mvvmsample.di.component

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import me.sparker0i.mvvmsample.di.module.AppModule
import me.sparker0i.mvvmsample.di.module.BuildersModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class, AppModule::class])
interface AppComponent {
    fun inject(app: Application)
}