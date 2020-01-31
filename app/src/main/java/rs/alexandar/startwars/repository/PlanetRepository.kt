package rs.alexandar.startwars.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import rs.alexandar.startwars.data.PlanetData
import rs.alexandar.startwars.model.Planet
import rs.alexandar.startwars.model.PlanetLikeRequest
import rs.alexandar.startwars.model.PlanetLikeResponse
import rs.alexandar.startwars.network.StarWarsApiService
import rs.alexandar.startwars.util.Constants
import rs.alexandar.startwars.util.StarWarsPreferencesManager

class PlanetRepository {
    private val planetsLiveData = MutableLiveData<Planet>()
    private var planetObservable: Single<Planet?>? = null
    private var planetLikeObservable: Single<PlanetLikeResponse?>? = null
    private val compositeDisposable = CompositeDisposable()
    private var context: Context? = null
    fun getPlanetsLiveData(context: Context?): MutableLiveData<Planet> {
        this.context = context
        planetObservable = StarWarsApiService.INSTANCE.value.kaminoPlanets
        val disposableSingleObserver: DisposableSingleObserver<Planet?> = object : DisposableSingleObserver<Planet?>() {
                // This is not good way of course for change likes number, but api for likes not working well

                override fun onSuccess(value: Planet?) {
                    val savedLikes: Int = StarWarsPreferencesManager.getInstance(context)!!.getData(Constants.LIKES_NUMBER_KEY)
                    if (savedLikes != -1 && savedLikes < Integer.valueOf(value?.likes.toString()) + 2) {
                        value!!.likes = StarWarsPreferencesManager.getInstance(context)!!.getData(Constants.LIKES_NUMBER_KEY).toString()
                    }
                    planetsLiveData.postValue(value)
                    PlanetData.planet = value;
                }

                override fun onError(e: Throwable) {}

            }
        planetObservable!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableSingleObserver)
        compositeDisposable.add(disposableSingleObserver)
        return planetsLiveData
    }

    fun triggerPlanetLike(planetLikeRequest: PlanetLikeRequest?) {
        planetLikeObservable =
            StarWarsApiService.INSTANCE.value.setPlanetLike(planetLikeRequest)
        val disposableSingleObserver: DisposableSingleObserver<PlanetLikeResponse?> = object : DisposableSingleObserver<PlanetLikeResponse?>() {
                override fun onSuccess(planetLikeResponse: PlanetLikeResponse?) {
                    getPlanetsLiveData(context)
                }
                override fun onError(e: Throwable) {}
            }
        planetLikeObservable!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableSingleObserver)
        compositeDisposable.add(disposableSingleObserver)
    }

    fun clear() {
        compositeDisposable.clear()
    }
}