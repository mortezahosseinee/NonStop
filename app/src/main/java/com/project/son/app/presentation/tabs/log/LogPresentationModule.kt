package com.project.son.app.presentation.tabs.log

import androidx.fragment.app.Fragment
import coil.ImageLoader
import com.project.son.library.base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule") {

    // AlbumList
    bind<LogViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { LogViewModel() }
    }

//    bind() from singleton { AlbumAdapter() }

    bind() from singleton { ImageLoader(instance()) }

    // AlbumDetails
//    bind<AlbumDetailViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
//        KotlinViewModelProvider.of(context) { AlbumDetailViewModel(instance(), instance()) }
//    }
}
