package com.app.senseaid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(val title: String, val img: String, val imgDescription: String) : Parcelable