package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_sign_up_page.*

class SignUpPage : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        signup_btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            signup_btn_signup->{
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
        }
    }
}
