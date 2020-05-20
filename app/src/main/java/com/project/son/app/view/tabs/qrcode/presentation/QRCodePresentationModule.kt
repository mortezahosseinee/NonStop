package com.project.son.app.view.tabs.qrcode.presentation

import androidx.fragment.app.Fragment
import coil.ImageLoader
import com.project.son.app.view.tabs.qrcode.MODULE_NAME
import com.project.son.library.base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule") {

    bind<QRCodeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { QRCodeViewModel() }
    }

    bind() from singleton { ImageLoader(instance()) }
}
