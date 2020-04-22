package com.kevinli5506.appta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_reward_page.*

class RewardPage : AppCompatActivity() {
private val list : ArrayList<SpecialPromo> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_page)
        setSupportActionBar(reward_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        reward_toolbar_tv.text = resources.getString(R.string.reward)
        list.addAll(SpecialPromoData.listPromo)
        reward_rv_reward.layoutManager = GridLayoutManager(this,2)
        val specialPromoAdapter = HomeSpecialPromoAdapter(list, Int.MAX_VALUE)
        reward_rv_reward.adapter = specialPromoAdapter
    }
}
