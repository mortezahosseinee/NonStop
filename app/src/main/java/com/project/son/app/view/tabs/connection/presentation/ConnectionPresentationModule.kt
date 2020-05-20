package com.project.son.app.view.tabs.connection.presentation

import androidx.fragment.app.Fragment
import coil.ImageLoader
import com.project.son.app.view.tabs.connection.MODULE_NAME
import com.project.son.library.base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule") {

    bind<ConnectionViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { ConnectionViewModel(instance("ActivityContext")) }
    }

    bind() from singleton { ImageLoader(instance()) }
}
