package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_home_page.view.*

class HomePage : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_navigation_home -> {
                    val fragment = HomeFragment()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.home)
                    supportActionBar?.hide()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home_navigation_news -> {
                    val fragment = NewsFragment()
                    supportActionBar?.show()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.news)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home_navigation_pick_up ->{
                    val fragment = PickUpFragment()
                    supportActionBar?.show()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.pick_up)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home_navigation_profile ->{
                    val fragment = ProfileFragment()
                    supportActionBar?.show()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.profile)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            home_toolbar.home_toolbar_tv.text = resources.getText(R.string.home)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                .commit()
        }
        setSupportActionBar(home_toolbar)
        supportActionBar?.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        home_bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}
