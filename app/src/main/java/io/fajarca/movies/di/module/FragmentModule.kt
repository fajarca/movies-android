package io.fajarca.movies.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.fajarca.movies.ui.HomeFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

}