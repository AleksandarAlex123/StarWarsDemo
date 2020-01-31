package rs.alexandar.startwars.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import rs.alexandar.startwars.model.PlanetLikeRequest
import rs.alexandar.startwars.room.db.UsersAppDatabase
import rs.alexandar.startwars.room.entity.User

class UserRepository(private val application: Application) {
    private val contactsAppDatabase: UsersAppDatabase
    private val disposable = CompositeDisposable()
    val usersLiveData = MutableLiveData<List<User>>()
    private val userLiveData = MutableLiveData<User?>()
    private var rowIdOfTheItemInserted: Long = 0
    fun createUser(deviceId: String?, like: Boolean, planetLikeRequest: PlanetLikeRequest?, planetRepository: PlanetRepository) {
        disposable.add(Completable.fromAction {
            rowIdOfTheItemInserted = contactsAppDatabase.userDAO
                .addUser(User(0, deviceId, like))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    planetRepository.triggerPlanetLike(planetLikeRequest)
                }

                override fun onError(e: Throwable) {}
            })
        )
    }

    fun updateUser(user: User?) {
        disposable.add(Completable.fromAction { contactsAppDatabase.userDAO.updateUser(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Toast.makeText(
                        application.applicationContext,
                        " user updated successfully ",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        " error occurred ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        )
    }

    fun getUser(deviceId: String?): MutableLiveData<User?> {
        disposable.add(contactsAppDatabase.userDAO.getUser(deviceId)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<User?> {
                override fun accept(user: User) {
                    userLiveData.postValue(user)
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable) {
                    userLiveData.postValue(null)
                }
            }
            )
        )
        return userLiveData
    }

    fun deleteUser(user: User?) {
        disposable.add(Completable.fromAction { contactsAppDatabase.userDAO.deleteUser(user) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    Toast.makeText(
                        application.applicationContext,
                        " user deleted successfully ",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(
                        application.applicationContext,
                        " error occurred ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        )
    }

    fun clear() {
        disposable.clear()
    }

    init {
        contactsAppDatabase = Room.databaseBuilder(
            application.applicationContext,
            UsersAppDatabase::class.java,
            "UsersDB"
        ).build()
        disposable.add(contactsAppDatabase.userDAO.users
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { user -> usersLiveData.postValue(user as List<User>?) },
                object : Consumer<Throwable?> {
                    override fun accept(t: Throwable) {
                    }
                }
            )
        )
    }
}