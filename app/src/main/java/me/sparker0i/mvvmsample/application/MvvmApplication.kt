package me.sparker0i.mvvmsample.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.sparker0i.mvvmsample.BuildConfig
import me.sparker0i.mvvmsample.di.component.DaggerAppComponent
import me.sparker0i.mvvmsample.di.module.AppModule
import me.sparker0i.mvvmsample.di.module.NetModule
import me.sparker0i.mvvmsample.utils.Constants
import timber.log.Timber
import javax.inject.Inject

class MvvmApplication : Application() , HasActivityInjector {
    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var INSTANCE: MvvmApplication

        fun ifHasNetwork(): Boolean {
            val cm = this.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.BASE_URL))
                .build().inject(this)
        INSTANCE = this
        initializeTimber()
        initializeStetho()
    }

    private fun initializeStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}