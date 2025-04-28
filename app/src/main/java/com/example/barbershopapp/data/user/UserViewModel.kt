package com.example.barbershopapp.data.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _username = MutableStateFlow("GUEST")
    val username : StateFlow<String> = _username

    private val db = FirebaseFirestore.getInstance()

    fun fetchCurrentUserData(userId: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _username.value = document.getString("name") ?: "GUEST"
                } else {
                    _username.value = "GUEST"
                }
            }
            .addOnFailureListener {
                _username.value = "GUEST"
            }
    }
}