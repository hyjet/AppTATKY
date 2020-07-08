package com.kevinli5506.appta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.kevinli5506.appta.Model.SpecialPromo
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_reward_detail_page.*

class RewardDetailPage : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_detail_page)
        val claimed = false  //Todo : make a object
        val specialPromo = intent.getParcelableExtra<SpecialPromo>(EXTRA_REWARD)
        val imageUrl ="${Constants.BASE_STORAGE_URL}${Constants.STORAGE_VOUCHER_URL}${specialPromo.imageFile}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(reward_detail_imgv_reward_image)
        reward_detail_tv_description_detail.text = specialPromo.detail
        reward_detail_tv_outlet_discount.text = specialPromo.name
        reward_detail_tv_voucher_code_content.text = specialPromo.code
        reward_detail_tv_voucher_price_content.text = specialPromo.price.toString()
        if(claimed == true){
            reward_detail_btn_claim.visibility = View.GONE
            reward_detail_tv_voucher_code_title.visibility=View.VISIBLE
            reward_detail_tv_voucher_code_content.visibility=View.VISIBLE
            reward_detail_tv_voucher_price_title .visibility=View.GONE
            reward_detail_tv_voucher_price_content.visibility=View.GONE
        }
        reward_detail_btn_claim.setOnClickListener(this)
        reward_detail_btn_back_navigation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            reward_detail_btn_back_navigation->{
                finish()
            }
            reward_detail_btn_claim->{

                val builder =AlertDialog.Builder(this)
                builder.setTitle("Klaim")
                builder.setMessage("Apakah anda yakin akan mengklaim hadiah ini?")
                builder.setPositiveButton("Yes"){_, _ ->
                    reward_detail_btn_claim.visibility = View.GONE
                    reward_detail_tv_voucher_code_title.visibility=View.VISIBLE
                    reward_detail_tv_voucher_code_content.visibility=View.VISIBLE
                    reward_detail_tv_voucher_price_title.visibility=View.GONE
                    reward_detail_tv_voucher_price_content.visibility=View.GONE
                    //Todo : change claimed to true
                }
                builder.setNegativeButton("No"){_, _ ->  }
                builder.show()
            }
        }
    }
    companion object{
        public var EXTRA_REWARD ="EXTRA_REWARD"
    }
}
