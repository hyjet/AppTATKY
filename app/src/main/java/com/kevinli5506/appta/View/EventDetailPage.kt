package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_event_detail_page.*

class EventDetailPage : AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_page)

        val event = intent.getParcelableExtra<EventDonation>(EXTRA_EVENT)

        val imageUrl ="${Constants.BASE_STORAGE_URL}${Constants.STORAGE_EVENTS_URL}${event.imageFile}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(event_detail_imgv_event_image)
        event_detail_tv_description_detail.text = event.description
        event_detail_tv_event_name.text = event.name
        event_detail_btn_back_navigation.setOnClickListener(this)
        event_detail_btn_donate.setOnClickListener(this)
    }
    companion object{
        val EXTRA_EVENT = "EXTRA_EVENT"
    }

    override fun onClick(v: View?) {
        when(v){
            event_detail_btn_donate->{
                val intent = Intent(this,
                    DonationPage::class.java)
                startActivity(intent)
            }
            event_detail_btn_back_navigation->{
                finish()
            }
        }
    }
}
