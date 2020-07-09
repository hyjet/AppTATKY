package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_launch_page.*

class LaunchPage : BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_page)
        launch_btn_login.setOnClickListener(this)
        launch_btn_signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            launch_btn_login->{
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
            launch_btn_signup->{
                val intent = Intent(this, SignUpPage::class.java)
                startActivity(intent)
            }
        }
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
