package com.kevinli5506.appta.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kevinli5506.appta.*
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.User
import com.kevinli5506.appta.Rest.ApiClient
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_home_page.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage : BaseActivity() {

    var verified: Boolean = false

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
                R.id.home_navigation_order -> {
                    val fragment = OrderListFragment()
                    supportActionBar?.show()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.order_list)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home_navigation_pick_up -> {
                    val fragment = PickUpFragment()
                    supportActionBar?.show()
                    home_toolbar.home_toolbar_tv.text = resources.getText(R.string.pick_up)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home_navigation_profile -> {
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

        val apiClient = ApiClient.getApiService(this)
        apiClient.getUser().enqueue(object : Callback<CommonResponseModel<List<User>>> {
            override fun onFailure(call: Call<CommonResponseModel<List<User>>>?, t: Throwable?) {
                Log.d("tes2", t?.message)
                Toast.makeText(
                    this@HomePage,
                    t?.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<CommonResponseModel<List<User>>>?,
                response: Response<CommonResponseModel<List<User>>>?
            ) {
                if (response?.code() == 200) {
                    val userResponse = response.body()
                    if (userResponse?.statusCode == 200) {
                        val list = userResponse.data

                        verified = list[0].verified == 1
                        if (verified) {
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
                            home_bottom_navigation.setOnNavigationItemSelectedListener(
                                mOnNavigationItemSelectedListener
                            )
                        } else {
                            if (savedInstanceState == null) {
                                val fragment =
                                    IdentityNotVerifiedFragment()
                                supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.home_container, fragment, fragment.javaClass.simpleName)
                                    .commit()
                            }
                            setSupportActionBar(home_toolbar)
                            supportActionBar?.hide()
                            supportActionBar?.setDisplayShowTitleEnabled(false)
                            home_bottom_navigation.visibility = View.GONE
                        }
                    }

                } else {
                    try {
                        val jObjError =
                            JSONObject(response!!.errorBody()?.string())
                        Toast.makeText(
                            this@HomePage,
                            jObjError.getJSONObject("data").getString("error"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@HomePage,
                            e.message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }

            }

        })


    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Exit")
            builder.setMessage("Apakah anda yakin akan keluar dari aplikasi?")
            builder.setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            builder.show()
        } else {
            super.onBackPressed()
        }
    }
}

