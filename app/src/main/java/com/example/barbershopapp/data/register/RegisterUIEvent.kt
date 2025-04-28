package com.example.barbershopapp.data.register

import java.util.Date

sealed class  RegisterUIEvent {
    data class NameChanged(val firstName: String) : RegisterUIEvent()
    data class PhoneChanged(val phone: Int) : RegisterUIEvent()
    data class EmailChanged(val email: String) : RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()

    data class DateChanged(val date: Date) : RegisterUIEvent()

    data class PrivacyPolicyCheckBoxClicked(val status: Boolean) : RegisterUIEvent()

    object RegisterButtonClicked : RegisterUIEvent()

}