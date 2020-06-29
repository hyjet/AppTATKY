package com.kevinli5506.appta

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class History (var code:String=""
                    ,var detail :String=""
                    ,var date:Calendar = Calendar.getInstance()
):Parcelable