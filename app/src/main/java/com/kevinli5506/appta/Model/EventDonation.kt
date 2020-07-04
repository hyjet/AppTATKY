package com.kevinli5506.appta.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDonation(
    var name: String = "",
    var description: String = "",
    var image: Int = 0
):Parcelable