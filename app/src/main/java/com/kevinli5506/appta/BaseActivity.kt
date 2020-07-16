package com.kevinli5506.appta

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kevinli5506.appta.Model.CommonResponseModel
import com.kevinli5506.appta.Model.NotificationResponse
import com.kevinli5506.appta.Rest.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


abstract class BaseActivity() : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        getNotifData()
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val handleReturn = super.dispatchTouchEvent(ev)
        val view = currentFocus
        val x = ev.getX().toInt()
        val y = ev.getY().toInt()
        if (view is EditText) {
            val innerView = currentFocus as EditText
            if (ev.getAction() == MotionEvent.ACTION_UP &&
                !getLocationOnScreen(innerView).contains(x, y)
            ) {
                hideSoftKeyboard(this)
                innerView.clearFocus()
            }
        }
        return handleReturn
    }

    open fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    protected open fun getLocationOnScreen(mEditText: EditText): Rect {
        val mRect = Rect()
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)
        mRect.left = location[0]
        mRect.top = location[1]
        mRect.right = location[0] + mEditText.width
        mRect.bottom = location[1] + mEditText.height
        return mRect
    }


    fun createIndentedText(
        text: String,
        marginFirstLine: Int,
        marginNextLine: Int
    ): SpannableString {
        val result: SpannableString = SpannableString(text)
        result.setSpan(
            LeadingMarginSpan.Standard(marginFirstLine, marginNextLine), 0, text.length, 0
        )
        return result
    }

    public fun createMoneyNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun getNotification(notificationId: Int, title: String, body: String) {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    fun getNotifData() {
        getObservable().subscribeWith(getObserver())
    }

    private fun getObserver(): DisposableObserver<CommonResponseModel<List<NotificationResponse>>> {
        return object :DisposableObserver<CommonResponseModel<List<NotificationResponse>>>(){
            override fun onComplete() {

            }

            override fun onNext(t: CommonResponseModel<List<NotificationResponse>>) {
                val data = t.data
                val notif = data[0]
                getNotification(1,notif.notification[0].title,notif.notification[0].message)
            }

            override fun onError(e: Throwable) {
                Toast.makeText(this@BaseActivity,e.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getObservable(): Observable<CommonResponseModel<List<NotificationResponse>>> {
        return ApiClient.getApiService(this)
            .getnotification()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



    companion object {
        val CHANNEL_ID = "CHANNEL_ID"
    }

}