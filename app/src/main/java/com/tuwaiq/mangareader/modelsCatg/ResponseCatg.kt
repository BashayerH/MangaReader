package com.tuwaiq.mangareader.modelsCatg

import com.google.gson.annotations.SerializedName

class ResponseCatg {

   @SerializedName("data")
   lateinit var listCatgut :List<CatgMangData>
}
