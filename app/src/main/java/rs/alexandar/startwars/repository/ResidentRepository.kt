package rs.alexandar.startwars.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import rs.alexandar.startwars.model.Resident
import rs.alexandar.startwars.network.StarWarsApiService

class ResidentRepository {
    private val residentLiveData = MutableLiveData<Resident>()
    private var residentObservable: Single<Resident?>? = null
    private val compositeDisposable = CompositeDisposable()

    fun getResidentLiveData(residentsUrl: String?): MutableLiveData<Resident> {
        residentObservable = StarWarsApiService.INSTANCE.value.getResident(residentsUrl)
        val disposableSingleObserver = object : DisposableSingleObserver<Resident?>() {
            override fun onSuccess(value: Resident?) {
                residentLiveData.postValue(value)
            }
            override fun onError(e: Throwable?) {
            }
        }

        residentObservable!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableSingleObserver)
        compositeDisposable.add(disposableSingleObserver)
        return residentLiveData
    }

    fun clear() {
        compositeDisposable.clear()
    }
}