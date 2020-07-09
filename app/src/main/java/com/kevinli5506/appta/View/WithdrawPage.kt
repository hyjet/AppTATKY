package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.R

class WithdrawPage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw_page)
        val onProcess = false //Todo:Make a object
        val fragment:Fragment
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
