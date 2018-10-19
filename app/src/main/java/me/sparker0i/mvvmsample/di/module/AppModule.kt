package me.sparker0i.mvvmsample.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.sparker0i.mvvmsample.database.Database
import me.sparker0i.mvvmsample.database.RatesDao
import me.sparker0i.mvvmsample.utils.Constants
import javax.inject.Singleton

@Module class AppModule(val app: Application) {
    @Provides @Singleton fun provideApplication(): Application = app

    @Provides @Singleton fun provideRatesDatabase(app: Application): Database = Room.databaseBuilder(app ,
            Database::class.java , Constants.DATABASE_NAME)
            .build()

    @Provides @Singleton fun provideRatesDao(database: Database): RatesDao = database.daoAccess()
}