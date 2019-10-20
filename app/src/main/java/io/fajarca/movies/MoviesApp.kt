package io.fajarca.movies

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fajarca.movies.di.component.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class MoviesApp : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        initTimber()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
