package com.project.son.app.presentation.tabs.log

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.project.son.R
import com.project.son.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_connection.*
import org.kodein.di.generic.instance

class LogFragment : BaseContainerFragment() {

    private val viewModel: LogViewModel by instance()

    override val layoutResourceId = R.layout.fragment_connection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = requireContext()

        setTextFont()
    }

    private fun setTextFont() {
        Typeface.createFromAsset(
            context?.assets,
            "iransans_fa.ttf"
        ).let {
            txv_choose_floor.typeface = it
        }
    }
}
