package com.project.son.app.presentation.main

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.project.son.R
import com.project.son.app.presentation.tabs.connection.ConnectionFragment
import com.project.son.app.presentation.tabs.log.LogFragment
import com.project.son.app.presentation.tabs.qrcode.QRCodeFragment
import com.project.son.app.presentation.tabs.shortcut.ShortcutFragment
import com.project.son.library.base.presentation.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var userScrollChange = false
    private var previousState: Int = 0

    override val layoutResId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
        initTabLayout()
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
