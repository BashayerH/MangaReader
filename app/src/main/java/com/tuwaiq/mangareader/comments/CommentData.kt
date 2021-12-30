package com.tuwaiq.mangareader.comments

import android.provider.ContactsContract
import com.google.firebase.Timestamp
import java.util.*

data class CommentData(
    val userEmail:String="",
    val userId:String="",
    val mangaId:String="",
    val msg:String="",
    val rating:Float=0F,
    val time: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    var expand:Boolean =false

)