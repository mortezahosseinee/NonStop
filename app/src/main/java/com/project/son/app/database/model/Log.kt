package com.project.son.app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Shortcut.TABLE_NAME)
data class Log(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Int,
    @ColumnInfo(name = DEVICE_ID)
    var deviceId: String,
    @ColumnInfo(name = FLOOR)
    var floor: Int,
    @ColumnInfo(name = DATE_TIME)
    var dateTime: Long
) {
    companion object {
        const val TABLE_NAME = "log"
        const val ID = "id"
        const val DEVICE_ID = "device_id"
        const val FLOOR = "floor"
        const val DATE_TIME = "date_time"
    }
}
