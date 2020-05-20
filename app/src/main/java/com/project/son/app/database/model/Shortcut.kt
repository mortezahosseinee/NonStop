package com.project.son.app.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Shortcut.TABLE_NAME)
data class Shortcut(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Shortcut.ID)
    var id: Int,
    @ColumnInfo(name = Shortcut.DEVICE_ID)
    var deviceId: String,
    @ColumnInfo(name = Shortcut.FLOOR)
    var floor: Int
) {
    companion object {
        const val TABLE_NAME = "shortcut"
        const val ID = "id"
        const val DEVICE_ID = "device_id"
        const val FLOOR = "floor_number"
    }
}
