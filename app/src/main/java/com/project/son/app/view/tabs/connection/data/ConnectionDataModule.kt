package com.project.son.app.view.tabs.connection.data

import androidx.room.Room
import com.project.son.app.database.DB_NAME
import com.project.son.app.database.MyDatabase
import com.project.son.app.view.tabs.connection.MODULE_NAME
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance

internal val dataModule = Kodein.Module("${MODULE_NAME}DataModule") {

    bind<MyDatabase>() with eagerSingleton {
        Room.databaseBuilder(
            instance("ApplicationContext"),
            MyDatabase::class.java,
            DB_NAME
        ).build()
    }
}
