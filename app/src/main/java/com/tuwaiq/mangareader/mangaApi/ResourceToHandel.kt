package com.tuwaiq.mangareader.mangaApi

import android.provider.VoicemailContract
import android.webkit.ConsoleMessage
import androidx.work.Operation
import com.google.android.gms.common.api.Status


data class ResourceToHandel <T>( val data:T, val message:String? =null){
    companion object{
        //handle success
        fun<T> success(data:T):ResourceToHandel<T>{
            return ResourceToHandel(data,null)
        }
        //handle Error
        fun<T> error(msg:String): ResourceToHandel<T?> {
            return ResourceToHandel(null,msg)
        }

    }
}