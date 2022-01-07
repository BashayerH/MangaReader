package com.tuwaiq.mangareader.modelsCatg

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class CatgMangData(
   val  title :String="",
   val url:String =""
) : Parcelable
