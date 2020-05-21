package com.project.son.library.base.presentation.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.project.son.library.base.R
import timber.log.Timber

abstract class BaseContainerFragment : InjectionFragment() {

    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutResourceId, null).also {
            Timber.v("onCreateView ${javaClass.simpleName}")
        }

    open fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) view = View(activity)

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("WrongConstant")
    open fun showSnackbar(message: String, positivity: Boolean) {
        try {
            val mSnackbar = Snackbar.make(requireView(), message, LENGTH_LONG)

            val txvSnackbarMessage = mSnackbar.view.findViewById<TextView>(R.id.snackbar_text)
            val txvSnackbarAction = mSnackbar.view.findViewById<TextView>(R.id.snackbar_action)

            txvSnackbarMessage.setTextColor(if (positivity) Color.GREEN else Color.RED)
            txvSnackbarAction.setTextColor(Color.WHITE)

            ViewCompat.setLayoutDirection(mSnackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL);

            Typeface.createFromAsset(
                context?.assets,
                "iransans_fa.ttf"
            ).let {
                txvSnackbarMessage.typeface = it
                txvSnackbarAction.typeface = it
            }
            mSnackbar.setAction("تأیید") {
                mSnackbar.dismiss()
            }.show()
        } catch (ignore: Exception) {
        }
    }
}
