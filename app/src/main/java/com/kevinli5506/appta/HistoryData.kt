package com.kevinli5506.appta

import com.kevinli5506.appta.Model.History
import java.util.*

object HistoryData {
    private val historyCode = "ABCDE"
    private val historyDetail = "ABCDEFGH"

    private val historyDate: Calendar
        get() {
            val time = Calendar.getInstance()
            time.set(2020,3,10,17,20,13)
            return time
        }

    val listHistory :ArrayList<History>
        get(){
            val arr = arrayListOf<History>()
            for (i in 1..5){
                val history = History(
                    historyCode,
                    historyDetail,
                    historyDate
                )
                arr.add(history)
            }
            return arr
        }
}