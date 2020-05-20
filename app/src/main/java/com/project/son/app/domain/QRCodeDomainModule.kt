package com.project.son.app.domain

import com.project.son.app.presentation.tabs.qrcode.MODULE_NAME
import org.kodein.di.Kodein

internal val domainModule = Kodein.Module("${MODULE_NAME}DomainModule") {

//    bind() from singleton { GetAlbumListUseCase(instance()) }
//
//    bind() from singleton { GetAlbumUseCase(instance()) }
}
