package rs.alexandar.startwars.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import rs.alexandar.startwars.data.PlanetData
import rs.alexandar.startwars.model.Planet
import rs.alexandar.startwars.model.Resident
import rs.alexandar.startwars.repository.PlanetRepository
import rs.alexandar.startwars.repository.ResidentRepository
import javax.inject.Inject

class ResidentActivityViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private val planetRepository: PlanetRepository
    private val residentRepository: ResidentRepository
    val planets: LiveData<Planet?>?
        get() = planetRepository.getPlanetsLiveData(getApplication())

    fun getResidentData(url: String?): LiveData<Resident?>? {
        return residentRepository.getResidentLiveData(url)
    }

    val residentsUrl: Array<String>
        get() = if (PlanetData.planet != null) {
            PlanetData.planet!!.residents
        } else emptyArray()

    fun clear() {
        residentRepository.clear()
        planetRepository.clear()
    }

    init {
        planetRepository = PlanetRepository()
        residentRepository = ResidentRepository()
    }
}