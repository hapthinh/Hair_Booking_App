package com.example.barbershopapp.data.firebase

import com.example.barbershopapp.data.stylist.Stylist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StylistUiState {
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _stylist = MutableStateFlow<List<Stylist>>(emptyList())
    val stylist : StateFlow<List<Stylist>> = _stylist

    init {
        getStylist()
    }

    private fun getStylist() {
        firestore.collection("stylist").addSnapshotListener {snapshot, error ->
            if (error != null){
                return@addSnapshotListener
            }
            val stylistList = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Stylist::class.java)
            } ?: emptyList()
            _stylist.value = stylistList
        }
    }
}