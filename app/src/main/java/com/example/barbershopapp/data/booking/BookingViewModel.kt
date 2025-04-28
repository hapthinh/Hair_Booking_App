package com.example.barbershopapp.data.booking

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class BookingViewModel {
    private fun saveBookingToFirebase(cityName: String, address: String, stylistName: String, date: Date) {
        val database = FirebaseFirestore.getInstance()
        val bookingRef = database.collection("bookings").document()

        val booking = mapOf(
            "cityName" to cityName,
            "address" to address,
            "stylistName" to stylistName,
            "date" to date.toString()
        )

        bookingRef.set(booking)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Booking", "Booking saved successfully")
                } else {
                    Log.e("Booking", "Failed to save booking: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Booking", "Failed to save booking: ${exception.message}")
            }
    }
}