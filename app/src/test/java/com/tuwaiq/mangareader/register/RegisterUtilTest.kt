package com.tuwaiq.mangareader.register



import com.airbnb.lottie.animation.content.Content
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegisterUtilTest {
    val register:RegisterUtil = RegisterUtil



    @Test
    fun validateRegister( ) {

        val result = register.validateRegister(
            emil = "es@f.com",
            password ="123456",
        userName="ws ",
        confPassword ="1234"
        )

    }


    @Test
    fun `empty userName or emil return false`(){
        val result = register.validateRegister(
            emil = "e@e.com",
            password ="123456",
            userName="eee ",
            confPassword ="123456"
        )
        assertThat(result).isTrue()
    }

   @Test
    fun `paasword not mach returns true`(){
        val result = register.validateRegister(
            emil = "es@f.com",
            password ="1234567",
            userName="eee",
            confPassword ="12567"
        )
       assertThat(result).isFalse()

    }


}