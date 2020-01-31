package rs.alexandar.startwars.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.alexandar.startwars.room.UserDAO
import rs.alexandar.startwars.room.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class UsersAppDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO
}