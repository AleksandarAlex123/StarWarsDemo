package rs.alexandar.startwars.util

import android.content.Context
import java.util.*

object DeviceDataManager {
    private var uniqueID: String? = null
    private const val UNIQUE_ID = "UNIQUE_ID"
    @Synchronized
    fun getDeviceId(context: Context): String? {
        if (uniqueID == null) {
            val sharedPrefs = context.getSharedPreferences(UNIQUE_ID, Context.MODE_PRIVATE)
            uniqueID = sharedPrefs.getString(UNIQUE_ID, null)
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString()
                val editor = sharedPrefs.edit()
                editor.putString(UNIQUE_ID, uniqueID)
                editor.commit()
            }
        }
        return uniqueID
    }
}