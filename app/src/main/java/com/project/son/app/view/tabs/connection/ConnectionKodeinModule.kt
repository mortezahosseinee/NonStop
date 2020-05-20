package com.project.son.app.view.tabs.connection

import com.project.son.app.feature.KodeinModuleProvider
import com.project.son.app.view.tabs.connection.data.dataModule
import com.project.son.app.view.tabs.connection.domain.domainModule
import com.project.son.app.view.tabs.connection.presentation.presentationModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Connection"

object ConnectionKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
