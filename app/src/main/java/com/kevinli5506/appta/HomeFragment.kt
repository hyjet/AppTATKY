package com.kevinli5506.appta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.home_imgbtn_withdrawal
import java.text.DecimalFormat


class HomeFragment : Fragment(),View.OnClickListener{
private val list: ArrayList<SpecialPromo> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(SpecialPromoData.listPromo)
        view.home_rv_special_promo.layoutManager = GridLayoutManager(view.context,2)
        val specialPromoAdapter = HomeSpecialPromoAdapter(list,4)
        val df = DecimalFormat("#,###")
        view.home_tv_point.text = df.format(UserData.point)
        view.home_rv_special_promo.adapter = specialPromoAdapter
        view.home_imgbtn_withdrawal.setOnClickListener(this)
        view.home_imgbtn_history.setOnClickListener(this)
        specialPromoAdapter.setOnItemClickCallBack(object :HomeSpecialPromoAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: SpecialPromo) {
                val intent = Intent(view.context,RewardDetailPage::class.java)
                intent.putExtra(RewardDetailPage.EXTRA_REWARD,data)
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            home_imgbtn_withdrawal->{

            }
            home_imgbtn_history->{

            }
            home_imgbtn_reward->{
                val intent = Intent(context,RewardPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_donation->{
                val intent = Intent(context,DonationPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_calculate->{
                val intent = Intent(context,CalculatePage::class.java)
                startActivity(intent)
            }
        }
    }

}
