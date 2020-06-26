package com.kevinli5506.appta

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecialPromo(
    var name: String = "",
    var detail: String = "",
    var image: Int = 0
):Parcelable