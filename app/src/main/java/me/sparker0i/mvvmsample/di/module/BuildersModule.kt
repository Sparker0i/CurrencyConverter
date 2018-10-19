package me.sparker0i.mvvmsample.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.sparker0i.mvvmsample.activity.MainActivity

@Module abstract class BuildersModule {
    @ContributesAndroidInjector abstract fun contributeMainActivity(): MainActivity
}