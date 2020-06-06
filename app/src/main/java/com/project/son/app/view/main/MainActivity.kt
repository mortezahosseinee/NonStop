package com.project.son.app.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.project.son.R
import com.project.son.app.view.tabs.connection.presentation.ConnectionFragment
import com.project.son.app.view.tabs.log.LogFragment
import com.project.son.app.view.tabs.qrcode.presentation.QRCodeFragment
import com.project.son.app.view.tabs.shortcut.ShortcutFragment
import com.project.son.library.base.presentation.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_connection.*

class MainActivity : BaseActivity() {

    private var userScrollChange = false
    private var previousState: Int = 0
    private val MY_CAMERA_REQUEST_CODE = 2000

    override val layoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTextFont()
        initViewPager()
        initTabLayout()
        initCameraPermission()

        Handler().postDelayed({
            ctl_splash.visibility = INVISIBLE
            ctl_view.visibility = VISIBLE
        }, 1500)
    }

    private fun initCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_REQUEST_CODE
            )
    }

    private fun setTextFont() {
        Typeface.createFromAsset(
            assets,
            "yagut_fa_b.ttf"
        ).let {
            txv_title.typeface = it
        }

        Typeface.createFromAsset(
            assets,
            "yagut_fa.ttf"
        ).let {
            txv_version.typeface = it
        }
    }

    private fun initViewPager() {
        viewPager.apply {
            adapter = MyViewPagerAdapter(
                supportFragmentManager,
                arrayListOf(
                    QRCodeFragment(),
                    ConnectionFragment(),
                    ShortcutFragment(),
                    LogFragment()
                )
            )

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    if (previousState == ViewPager.SCROLL_STATE_DRAGGING && state == ViewPager.SCROLL_STATE_SETTLING)
                        userScrollChange = true
                    else if (previousState == ViewPager.SCROLL_STATE_SETTLING && state == ViewPager.SCROLL_STATE_IDLE)
                        userScrollChange = false

                    previousState = state
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (userScrollChange)
                        tabLayout.selectTab(tabLayout.getTabAt(position))
                }

                override fun onPageSelected(position: Int) {
                }
            })
        }
    }

    private fun initTabLayout() {
        tabLayout.apply {
            addTab(
                newTab().apply {
                    setText(R.string.qr_code_tab)
                    setIcon(R.drawable.ic_qr_code)
                }
            )

            addTab(
                newTab().apply {
                    setText(R.string.connection_tab)
                    setIcon(R.drawable.ic_connection)
                }
            )

            addTab(
                newTab().apply {
                    setText(R.string.shortcut_tab)
                    setIcon(R.drawable.ic_shortcut)
                }
            )

            addTab(
                newTab().apply {
                    setText(R.string.log_tab)
                    setIcon(R.drawable.ic_log)
                }
            )

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab?.position ?: 0
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }
}
