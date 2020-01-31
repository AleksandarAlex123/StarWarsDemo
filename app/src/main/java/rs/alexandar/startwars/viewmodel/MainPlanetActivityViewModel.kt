package rs.alexandar.startwars.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rs.alexandar.startwars.model.Planet
import rs.alexandar.startwars.model.PlanetLikeRequest
import rs.alexandar.startwars.repository.PlanetRepository
import rs.alexandar.startwars.repository.UserRepository
import rs.alexandar.startwars.room.entity.User
import javax.inject.Inject

class MainPlanetActivityViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {
    private val planetRepository: PlanetRepository
    private val userRepository: UserRepository
    fun getAllUsers(): LiveData<List<User>>? {
        return userRepository.usersLiveData
    }

    fun createUser(deviceId: String?, like: Boolean, planetLikeRequest: PlanetLikeRequest?) {
        userRepository.createUser(deviceId, like, planetLikeRequest, planetRepository)
    }

    fun getUser(deviceId: String?): MutableLiveData<User?>? {
        return userRepository.getUser(deviceId)
    }

    fun updateUser(user: User?) {
        userRepository.updateUser(user)
    }

    fun deleteUser(user: User?) {
        userRepository.deleteUser(user)
    }

    val planets: LiveData<Planet?>?
        get() = planetRepository.getPlanetsLiveData(getApplication())

    fun triggerPlanetLike(planetLikeRequest: PlanetLikeRequest?) {
        planetRepository.triggerPlanetLike(planetLikeRequest)
    }

    fun clear() {
        planetRepository.clear()
    }

    init {
        planetRepository = PlanetRepository()
        userRepository = UserRepository(application)
    }
}