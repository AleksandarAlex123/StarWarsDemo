package rs.alexandar.startwars.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User {
    @ColumnInfo(name = "device_id")
    var deviceId: String? = null
    @ColumnInfo(name = "like")
    var isLike = false
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    constructor() {
    }

    constructor(id: Long, deviceId: String?, like: Boolean) {
        this.id = id
        this.deviceId = deviceId
        isLike = like
    }

}