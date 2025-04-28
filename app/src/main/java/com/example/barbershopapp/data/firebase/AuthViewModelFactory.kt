package com.example.barbershopapp.data.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthViewModelFactory(private val authRepo: AuthRepo, private val cityRepo: CityUIState, private val stylistRepo: StylistUiState) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(authRepo,cityRepo,stylistRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
