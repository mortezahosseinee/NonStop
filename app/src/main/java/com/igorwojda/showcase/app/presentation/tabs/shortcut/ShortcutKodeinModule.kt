package com.igorwojda.showcase.app.presentation.tabs.shortcut

import com.igorwojda.showcase.app.data.dataModule
import com.igorwojda.showcase.app.domain.domainModule
import com.igorwojda.showcase.app.feature.KodeinModuleProvider
import org.kodein.di.Kodein

internal const val MODULE_NAME = "QRCode"

object ShortcutKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
