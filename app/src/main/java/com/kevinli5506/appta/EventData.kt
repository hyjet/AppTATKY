package com.kevinli5506.appta

import com.kevinli5506.appta.Model.EventDonation

object EventData {
    private val eventName = "Nama Event"
    private val eventDescription = "Event ini blablablablbalbalbalbalbalbalbalbalbal"
    private val eventImage = R.drawable.sir_salon

    val listEvent : ArrayList<EventDonation>
        get() {
            val list = arrayListOf<EventDonation>()
            for (i in 1..8){
                val event = EventDonation()
                event.name = eventName
                event.description = eventDescription
                event.image = eventImage
                list.add(event)
            }
            return list
        }
}