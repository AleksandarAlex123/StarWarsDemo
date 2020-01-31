package rs.alexandar.startwars.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import rs.alexandar.startwars.viewmodel.MainPlanetActivityViewModel
import rs.alexandar.startwars.viewmodel.ResidentActivityViewModel

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(MainPlanetActivityViewModel::class)
    protected abstract fun mainPlanetActivityViewModel(mainPlanetActivityViewModel: MainPlanetActivityViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(ResidentActivityViewModel::class)
    protected abstract fun residentActivityViewModel(mainPlanetActivityViewModel: ResidentActivityViewModel?): ViewModel?
}