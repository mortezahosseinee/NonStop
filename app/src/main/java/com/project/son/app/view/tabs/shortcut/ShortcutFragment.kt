package com.project.son.app.view.tabs.shortcut

import android.os.Bundle
import android.view.View
import com.project.son.R
import com.project.son.library.base.presentation.fragment.BaseContainerFragment
import org.kodein.di.generic.instance

class ShortcutFragment : BaseContainerFragment() {

    private val viewModel: ShortcutViewModel by instance()

    override val layoutResourceId = R.layout.fragment_shortcut

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
