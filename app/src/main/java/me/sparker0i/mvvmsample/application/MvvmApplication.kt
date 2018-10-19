package me.sparker0i.mvvmsample.application

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.sparker0i.mvvmsample.di.component.DaggerAppComponent
import me.sparker0i.mvvmsample.di.module.AppModule
import javax.inject.Inject

class MvvmApplication : Application() , HasActivityInjector {
    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}