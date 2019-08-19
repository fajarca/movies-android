package io.fajarca.movies.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.fajarca.movies.MoviesApp
import io.fajarca.movies.api.ApiService
import io.fajarca.movies.db.MoviesDatabase
import io.fajarca.movies.util.DATABASE_NAME
import io.fajarca.movies.util.PREF_NAME
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun provideContext(app: MoviesApp) : Context = app

    @Provides
    @Singleton
    fun provideApplications(app : MoviesApp) : Application = app

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesPreference(app : MoviesApp) : SharedPreferences  = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesSharedPreference(sharedPreferences: SharedPreferences) : SharedPreferences.Editor = sharedPreferences.edit()


}