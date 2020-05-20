package com.project.son.app.view.tabs.connection.domain

import com.project.son.app.view.tabs.connection.MODULE_NAME
import org.kodein.di.Kodein

internal val domainModule = Kodein.Module("${MODULE_NAME}DomainModule") {

//    bind() from singleton { GetAlbumListUseCase(instance()) }
//
//    bind() from singleton { GetAlbumUseCase(instance()) }
}
