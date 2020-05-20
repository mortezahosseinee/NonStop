package com.project.son.app.presentation.tabs.connection

import com.project.son.app.data.dataModule
import com.project.son.app.domain.domainModule
import com.project.son.app.feature.KodeinModuleProvider
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Connection"

object ConnectionKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}