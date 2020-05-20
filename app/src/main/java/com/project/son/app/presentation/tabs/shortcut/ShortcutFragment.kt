package com.project.son.app.presentation.tabs.shortcut

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.project.son.R
import com.project.son.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.fragment_shortcut.*
import org.kodein.di.generic.instance

class ShortcutFragment : BaseContainerFragment() {

    private val viewModel: ShortcutViewModel by instance()

    override val layoutResourceId = R.layout.fragment_shortcut

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
