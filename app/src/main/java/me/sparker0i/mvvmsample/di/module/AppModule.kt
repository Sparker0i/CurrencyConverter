package me.sparker0i.mvvmsample.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule(val app: Application) {
    @Provides @Singleton fun provideApplication(): Application = app
}