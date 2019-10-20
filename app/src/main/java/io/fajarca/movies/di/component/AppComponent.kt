package io.fajarca.movies.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.fajarca.movies.MoviesApp
import io.fajarca.movies.di.module.ActivityBuilder
import io.fajarca.movies.di.module.AppModule
import io.fajarca.movies.di.module.NetworkModule
import io.fajarca.movies.di.module.RepositoryModule
import io.fajarca.movies.di.module.FragmentModule
import io.fajarca.movies.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentModule::class,
    ViewModelModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MoviesApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MoviesApp)
}
