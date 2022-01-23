package com.tuwaiq.mangareader.mangaApi.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val uploaded_at: String,
    val url: String,
    val views_count: Int
) : Parcelable