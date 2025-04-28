package com.example.barbershopapp.data.stylist

import com.google.firebase.firestore.PropertyName

data class Stylist(
    val id: String? = null, // Chuyển đổi id thành String
    @PropertyName("name") val name: String? = null,
    @PropertyName("phone") val phone: Int? = null,
    @PropertyName("email") val email: String? = null
)
