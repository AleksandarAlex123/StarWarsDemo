package rs.alexandar.startwars.util

import android.content.Context
import android.content.SharedPreferences

class StarWarsPreferencesManager private constructor(context: Context?) {
    private val sharedPreferences: SharedPreferences?
    fun saveData(key: String?, value: Int) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putInt(key, value)
        prefsEditor.commit()
    }

    fun saveData(key: String?, value: Boolean) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.commit()
    }

    fun getBoolData(key: String?): Boolean {
        return sharedPreferences?.getBoolean(key, false) ?: false
    }

    fun getData(key: String?): Int {
        return sharedPreferences?.getInt(key, -1) ?: -1
    }

    companion object {
        private var starWarsPreferencesManager: StarWarsPreferencesManager? = null
        fun getInstance(context: Context?): StarWarsPreferencesManager? {
            if (starWarsPreferencesManager == null) {
                starWarsPreferencesManager =
                    StarWarsPreferencesManager(context)
            }
            return starWarsPreferencesManager
        }
    }

    init {
        sharedPreferences = context!!.getSharedPreferences(
            "StarWarsPreferences",
            Context.MODE_PRIVATE
        )
    }
}