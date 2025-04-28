package com.example.barbershopapp

import android.app.Application
import com.google.firebase.FirebaseApp

class BarBerShopApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}