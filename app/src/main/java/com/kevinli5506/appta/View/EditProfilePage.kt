package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kevinli5506.appta.R
import com.kevinli5506.appta.UserData
import kotlinx.android.synthetic.main.activity_edit_profile_page.*

class EditProfilePage : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)
        edit_profile_btn_save.setOnClickListener(this)
        edit_profile_edt_name.setText(UserData.name)
        edit_profile_edt_email.setText(UserData.email)
        edit_profile_edt_phone_number.setText(UserData.phone)
    }

    override fun onClick(v: View?) {
        when(v){
            edit_profile_btn_save->{
                val name = edit_profile_edt_name.text.toString()
                val email = edit_profile_edt_email.text.toString()
                val phone = edit_profile_edt_phone_number.text.toString()
                finish()
            }
        }
    }
}
