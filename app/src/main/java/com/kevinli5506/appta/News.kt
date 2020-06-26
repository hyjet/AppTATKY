package com.kevinli5506.appta

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class News(
    var title: String = "",
    var rating: Float = 0f,
    var time: Calendar = Calendar.getInstance(),
    var image: Int = 0,
    var description: String = ""
) : Parcelable