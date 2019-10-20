package io.fajarca.movies.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.movies.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}