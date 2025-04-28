package com.example.barbershopapp.data.register

import ValidationResult
import Validator
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.barbershopapp.data.RegisteUIState
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {
    private val TAG = RegisterViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegisteUIState())

    var allValidationsPassed = mutableStateOf(false)

    var registerInProgress = mutableStateOf(false)


    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.NameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    name = event.firstName
                )
            }
            is RegisterUIEvent.DateChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    birthday = event.date
                )
            }
            is RegisterUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }
            is RegisterUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }
            is RegisterUIEvent.PhoneChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    phone = event.phone
                )
            }
            is RegisterUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
            RegisterUIEvent.RegisterButtonClicked -> {
                registter()
            }
            else -> {
                Log.e(TAG, "Unknown event")
            }
        }
        validateData()
        printState()
    }


     fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }

     fun registter() {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )
    }

     fun validateData() {
        val nameResult = Validator.validateName(
            name = registrationUIState.value.name
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val phoneResult = Validator.validatePhone(
            phone = registrationUIState.value.phone.toString()
        )
        val dateResult = if (registrationUIState.value.birthday != null) {
            Validator.validateDate(date = registrationUIState.value.birthday!!)
        } else {
            ValidationResult(status = false)
        }
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "nameResult= ${nameResult}")
        Log.d(TAG, "emailResult= ${emailResult}")
        Log.d(TAG, "passwordResult= ${passwordResult}")
        Log.d(TAG, "phoneResult= ${phoneResult}")
        Log.d(TAG, "dateResult= ${dateResult}")
        Log.d(TAG, "privacyPolicyResult= ${privacyPolicyResult}")

        allValidationsPassed.value = nameResult.status &&
                emailResult.status && passwordResult.status && privacyPolicyResult.status
                && passwordResult.status && phoneResult.status && dateResult.status

        registrationUIState.value = registrationUIState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            phoneError = phoneResult.status,
            dateError = dateResult.status,
            privacyPolicyError = privacyPolicyResult.status,
        )

    }
     fun createUserInFirebase(email: String, password: String) {

        registerInProgress.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                Log.d(TAG, "Inside_OnCompleteListener")
                Log.d(TAG, " isSuccessful = ${task.isSuccessful}")
                if(task.isSuccessful){
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    userId?.let {
                        saveUserToDatabase(it)
                    }
                    BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside_OnFailureListener")
                Log.d(TAG, "Exception= ${it.message}")
                Log.d(TAG, "Exception= ${it.localizedMessage}")
            }
    }
    private fun saveUserToDatabase(userId: String) {
        val database = FirebaseFirestore.getInstance()
        val userRef = database.collection("users").document(userId)
        val user = mapOf(
            "uid" to userId,
            "name" to registrationUIState.value.name,
            "email" to registrationUIState.value.email,
            "phone" to registrationUIState.value.phone,
            "birthday" to registrationUIState.value.birthday?.toString(),
            "password" to registrationUIState.value.password,
            "privacyPolicyAccepted" to registrationUIState.value.privacyPolicyAccepted,
            "role" to registrationUIState.value.role
        )

        userRef.set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User data saved successfully")
                } else {
                    Log.e(TAG, "Failed to save user data: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to save user data: ${exception.message}")
            }
    }

}
