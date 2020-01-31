package rs.alexandar.startwars.dagger

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import rs.alexandar.startwars.R
import rs.alexandar.startwars.room.UserDAO
import rs.alexandar.startwars.room.db.UsersAppDatabase
import javax.inject.Singleton

@Module
class DBModule {
    @Module
    inner class DbModule {
        /*
         * The method returns the Database object
         * */
        @Provides
        @Singleton
        fun provideDatabase(application: Application): UsersAppDatabase {
            return Room.databaseBuilder(
                application,
                UsersAppDatabase::class.java, application.getString(R.string.users_db_name)
            )
                .allowMainThreadQueries().build()
        }

        /*
         * We need the UserDao module.
         * For this, We need the UsersAppDatabase object
         * So we will define the providers for this here in this module.
         * */
        @Provides
        @Singleton
        fun provideMovieDao(usersAppDatabase: UsersAppDatabase): UserDAO? {
            return usersAppDatabase.userDAO
        }
    }
}