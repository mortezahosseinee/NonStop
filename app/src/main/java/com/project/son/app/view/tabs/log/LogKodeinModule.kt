package com.project.son.app.view.tabs.log

import com.project.son.app.view.tabs.qrcode.data.dataModule
import com.project.son.app.view.tabs.qrcode.domain.domainModule
import com.project.son.app.feature.KodeinModuleProvider
import org.kodein.di.Kodein

internal const val MODULE_NAME = "QRCode"

object LogKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
