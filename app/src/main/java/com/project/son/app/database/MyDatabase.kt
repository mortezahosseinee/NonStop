package com.project.son.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.son.app.database.daos.LogDao
import com.project.son.app.database.daos.ShortcutDao
import com.project.son.app.database.model.Shortcut

const val DB_VERSION = 1
const val DB_NAME = "NonStopDatabase.db"

@Database(entities = [Shortcut::class], version = DB_VERSION)
abstract class MyDatabase : RoomDatabase() {
    abstract fun shortcutDao(): ShortcutDao
    abstract fun logDao(): LogDao
}
