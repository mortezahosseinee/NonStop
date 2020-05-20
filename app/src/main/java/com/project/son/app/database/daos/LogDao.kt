package com.project.son.app.database.daos

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.project.son.app.database.model.Log

@Keep
@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(log: Log)
}
