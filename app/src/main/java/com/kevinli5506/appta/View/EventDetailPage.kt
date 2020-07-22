package com.kevinli5506.appta.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.kevinli5506.appta.BaseActivity
import com.kevinli5506.appta.Model.EventDonation
import com.kevinli5506.appta.R
import com.kevinli5506.appta.Rest.Constants
import kotlinx.android.synthetic.main.activity_event_detail_page.*
import java.text.SimpleDateFormat
import java.util.*

class EventDetailPage : BaseActivity(), View.OnClickListener {
    lateinit var event: EventDonation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail_page)


        event = intent.getParcelableExtra(EXTRA_EVENT)

        val imageUrl =
            "${Constants.BASE_STORAGE_URL}${Constants.STORAGE_EVENTS_URL}${event.imageFile}"
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.image_broken)
            .fallback(R.drawable.image_null)
            .placeholder(R.drawable.image_loading)
            .into(event_detail_imgv_event_image)
        event_detail_tv_description_detail.text = createIndentedText(event.description, 100, 0)
        event_detail_tv_event_name.text = event.name
        event_detail_btn_back_navigation.setOnClickListener(this)
        event_detail_btn_donate.setOnClickListener(this)
        val StDformatter = SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH)
        val date: Date = StDformatter.parse(event.untilDateString)
        val DtSformatter = SimpleDateFormat("dd MMMM YYYY", Locale.ENGLISH)
        val dateString = DtSformatter.format(date)
        val untilDateString = "Sampai $dateString"
        event_detail_tv_until_date.text = untilDateString
    }


    override fun onClick(v: View?) {
        when (v) {
            event_detail_btn_donate -> {
                val intent = Intent(
                    this,
                    DonationPage::class.java
                )
                intent.putExtra(EXTRA_EVENT_ID, event)
                startActivity(intent)
            }
            event_detail_btn_back_navigation -> {
                finish()
            }
        }
    }

    companion object {
        val EXTRA_EVENT = "EXTRA_EVENT"
        val EXTRA_EVENT_ID = "EXTRA_EVENT_ID"
    }

}
