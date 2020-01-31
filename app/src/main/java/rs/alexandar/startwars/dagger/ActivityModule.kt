package rs.alexandar.startwars.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import rs.alexandar.startwars.view.MainPlanetActivity
import rs.alexandar.startwars.view.ResidentActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainPlanetActivity?

    @ContributesAndroidInjector
    abstract fun contributeResidentActivity(): ResidentActivity?
}