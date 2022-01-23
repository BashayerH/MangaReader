package com.tuwaiq.mangareader.mangaApi.models

import android.os.Parcelable
import com.google.firebase.encoders.annotations.Encodable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Data(
    val alternative_titles: List<String>,
    val authors: List<String> = emptyList(),
    val chapters: List<Chapter> = emptyList(),
    val description: String ="",
    val genres: List<String> = emptyList(),
    val last_updated: String ="",
    val rating: Double =0.0,
    val rating_count: Int= 0,
    val status: String ="",
    val title: String ="",
    val views_count: String =""
): Parcelable