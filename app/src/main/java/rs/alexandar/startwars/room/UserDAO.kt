package rs.alexandar.startwars.room

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import rs.alexandar.startwars.room.entity.User

@Dao
interface UserDAO {
    @Insert
    fun addUser(user: User?): Long

    @Update
    fun updateUser(user: User?)

    @Delete
    fun deleteUser(user: User?)

    @get:Query("select * from users")
    val users: Flowable<List<User?>?>

    @Query("select * from users where device_id ==:deviceId")
    fun getUser(deviceId: String?): Single<User?>
}