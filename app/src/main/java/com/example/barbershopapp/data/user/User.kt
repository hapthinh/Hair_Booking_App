package com.example.barbershopapp.data.user

import com.google.firebase.firestore.PropertyName

data class User(
    val uid: String? = null,
    @PropertyName("name") val name: String? = null,
    @PropertyName("phone") val phone: Int? = null,
    @PropertyName("email") val email: String? = null,
    @PropertyName("birthday") val birthday: String? = null,
    @PropertyName("password") val password: String? = null,
    @PropertyName("privacyPolicyAccepted") val privacyPolicyAccepted: Boolean? = null,
    @PropertyName("role") val role: String? = null
)
