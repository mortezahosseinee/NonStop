package com.project.son.app.data

import com.project.son.app.presentation.tabs.qrcode.MODULE_NAME
import org.kodein.di.Kodein

internal val dataModule = Kodein.Module("${MODULE_NAME}DataModule") {

//    bind<AlbumRepository>() with singleton { AlbumRepositoryImpl(instance()) }
//
//    bind() from singleton { instance<Retrofit>().create(AlbumRetrofitService::class.java) }
}
