package com.project.son.app.view.tabs.log

import android.os.Bundle
import android.view.View
import com.project.son.R
import com.project.son.library.base.presentation.fragment.BaseContainerFragment
import org.kodein.di.generic.instance

class LogFragment : BaseContainerFragment() {

    private val viewModel: LogViewModel by instance()

    override val layoutResourceId = R.layout.fragment_log

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
