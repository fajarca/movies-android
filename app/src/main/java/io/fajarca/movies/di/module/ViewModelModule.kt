package io.fajarca.movies.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.fajarca.movies.di.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory

   /* @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel : LoginViewModel) : ViewModel*/

}