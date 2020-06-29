package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_withdraw_page.*

class WithdrawPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw_page)
        val onProcess = false //Todo:Make a object
        var fragment:Fragment
        if(onProcess){
            fragment = WithdrawOnProcessFragment()
        }
        else{
            fragment = WithdrawDetailFragment()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.withdraw_container, fragment, fragment.javaClass.simpleName)
            .commit()

    }


}
