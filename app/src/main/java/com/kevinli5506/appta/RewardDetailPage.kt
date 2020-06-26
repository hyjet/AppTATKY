package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reward_detail_page.*

class RewardDetailPage : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_detail_page)
        val specialPromo = intent.getParcelableExtra<SpecialPromo>(EXTRA_REWARD)
        Glide.with(this)
            .load(specialPromo.image)
            .into(reward_detail_imgv_reward_image)
        reward_detail_tv_description_detail.text = specialPromo.detail
        reward_detail_tv_outlet_discount.text = specialPromo.name
        reward_detail_btn_back_navigation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            reward_detail_btn_back_navigation->{
                finish()
            }
        }
    }
    companion object{
        public var EXTRA_REWARD ="EXTRA_REWARD"
    }
}
