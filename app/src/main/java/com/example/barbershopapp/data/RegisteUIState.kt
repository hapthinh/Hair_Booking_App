package com.example.barbershopapp.data

import java.util.Date

data class RegisteUIState(
    var name:String = "",
    var email:String = "",
    var birthday:Date? = null,
    var password:String = "",
    var phone: Int = 0,
    var privacyPolicyAccepted:Boolean = false,
    val role: String = "Customer",

    var nameError:Boolean = false,
    var emailError:Boolean = false,
    var phoneError:Boolean = false,
    var dateError:Boolean = false,
    var passwordError: Boolean = false,
    var privacyPolicyError:Boolean = false,

)