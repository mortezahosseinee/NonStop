package com.project.son.app.database.daos

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.project.son.app.database.model.Shortcut

@Keep
@Dao
interface ShortcutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShortcut(shortcut: Shortcut)
}
