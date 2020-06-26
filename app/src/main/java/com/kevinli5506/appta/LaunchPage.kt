package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_launch_page.*

class LaunchPage : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_page)

    }

    override fun onClick(v: View?) {
        when(v){
            launch_btn_login->{

            }
            launch_btn_signup->{

            }
        }
    }
}
