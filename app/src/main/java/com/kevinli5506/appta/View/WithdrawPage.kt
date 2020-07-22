package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.R
import kotlinx.android.synthetic.main.activity_history_page.*
import kotlinx.android.synthetic.main.activity_withdraw_page.*

class WithdrawPage : BaseActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw_page)

        withdraw_btn_back.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v){
            withdraw_btn_back->{
                finish()
            }
        }
    }


}
