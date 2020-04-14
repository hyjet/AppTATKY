package com.kevinli5506.appta

import java.util.*
import kotlin.collections.ArrayList

object NewsData {
    private val newsTitle = "Lorem Ipsum"
    private val newsRating = 4f
    private val newsDate1:Calendar
    get() {
        val time = Calendar.getInstance()
        time.set(2020,3,10,17,20,13)
        return time
    }
    private val newsDate2:Calendar
        get() {
            val time = Calendar.getInstance()
            time.set(2020,3,4,17,20,13)
            return time
        }
    private val newsImage:Int = R.drawable.sir_salon

    val listNews :ArrayList<News>
    get(){
        val arr = arrayListOf<News>()
        for (i in 1..5){
            val news = News()
            news.title= newsTitle
            news.rating= newsRating
            news.time = if(i%2==0) newsDate1 else newsDate2
            news.image = newsImage
            arr.add(news)
        }
        return arr
    }
}