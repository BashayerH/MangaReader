package com.tuwaiq.mangareader.register

import android.provider.SyncStateContract
import com.google.android.gms.common.internal.Constants
import com.tuwaiq.mangareader.LoginData

object RegisterUtil {
    var data = LoginData()
    fun validateRegister(
       emil:String =data.emil,
        password:String =data.password,
         userName:String =data.userName,
        confPassword:String=data.confPass
    ):Boolean{
        if (emil.isEmpty() || userName.isEmpty() || password.isEmpty()){
            return false
        }
        if (password != confPassword){
            return false
        }
        if (password.count{ it.isDigit()} < 5){
            return false
        }
        return true
    }
}