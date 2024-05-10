package com.example.searchcollector.activities.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItem(
    val dateTime: String,
    val displaySiteName: String,
    val imageUrl: String
): Parcelable
