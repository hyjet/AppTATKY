package com.kevinli5506.appta

import java.text.SimpleDateFormat
import java.util.*

data class News(
    var title: String ="",
    var rating: Float = 0f,
    var time:Calendar = Calendar.getInstance(),
    var image: Int = 0
)