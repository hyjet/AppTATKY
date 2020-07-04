package com.kevinli5506.appta.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinli5506.appta.EventAdapter
import com.kevinli5506.appta.EventData
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.R
import com.kevinli5506.appta.UserData
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.home_imgbtn_withdrawal
import java.text.DecimalFormat


class HomeFragment : Fragment(),View.OnClickListener{
private val list: ArrayList<EventDonation> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(EventData.listEvent)
        view.home_rv_event.layoutManager = GridLayoutManager(view.context,2)
        val evenAdapter = EventAdapter(list, 8)
        val df = DecimalFormat("#,###")
        view.home_tv_point.text = df.format(UserData.point)
        view.home_rv_event.adapter = evenAdapter
        view.home_imgbtn_withdrawal.setOnClickListener(this)
        view.home_imgbtn_history.setOnClickListener(this)
        view.home_imgbtn_reward.setOnClickListener(this)
        view.home_imgbtn_donation.setOnClickListener(this)
        view.home_imgbtn_calculate.setOnClickListener(this)
        evenAdapter.setOnItemClickCallBack(object :
            EventAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: EventDonation) {
                val intent = Intent(view.context,
                    EventDetailPage::class.java)
                intent.putExtra(EventDetailPage.EXTRA_EVENT,data)
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        when(v){
            home_imgbtn_withdrawal->{
                val intent = Intent(context,
                    WithdrawPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_history->{
                val intent = Intent(context,
                    HistoryPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_reward->{
                val intent = Intent(context,
                    RewardPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_donation->{
                val intent = Intent(context,
                    EventPage::class.java)
                startActivity(intent)
            }
            home_imgbtn_calculate->{
                val intent = Intent(context,
                    CalculatePage::class.java)
                startActivity(intent)
            }
        }
    }

}
