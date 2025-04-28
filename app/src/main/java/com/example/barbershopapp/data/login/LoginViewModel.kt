package com.example.barbershopapp.data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.barbershopapp.Screen.Admin.AdminHomeScreen
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                if (allValidationsPassed.value) {
                    login()
                } else {
                    Log.d(TAG, "Validation failed")
                }
            }
        }
        validateData()
    }

//    private fun login() {
//        loginInProgress.value = true
//        val email = loginUIState.value.email
//        val password = loginUIState.value.password
//
//        FirebaseAuth
//            .getInstance()
//            .signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                Log.d(TAG, "Inside_login_success")
//                if (it.isSuccessful) {
//                    val userId = FirebaseAuth.getInstance().currentUser?.uid
//                    userId?.let { id ->
//                        fetchUserRoleAndNavigate(id)
//                    }
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Inside_login_failure: ${it.localizedMessage}")
//                loginInProgress.value = false
//            }
//    }


//private fun login() {
//    loginInProgress.value = true
//    val email = loginUIState.value.email
//    val password = loginUIState.value.password
//
//    // Xác thực thông qua FirebaseAuth
//    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Xác thực thành công, lấy UID của người dùng từ FirebaseAuth
//                val userId = FirebaseAuth.getInstance().currentUser?.uid
//                if (userId != null) {
//                    fetchUserRoleAndNavigate(userId)
//                } else {
//                    Log.d(TAG, "Error: UID is null after login.")
//                    loginInProgress.value = false
//                }
//            } else {
//                Log.d(TAG, "Login failed: ${task.exception?.localizedMessage}")
//                loginInProgress.value = false
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d(TAG, "Login failed: ${exception.localizedMessage}")
//            loginInProgress.value = false
//        }
//}

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        // Sử dụng FirebaseAuth để xác thực
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        fetchUserRoleAndNavigate(userId)
                    } else {
                        Log.d(TAG, "Error: UID is null after login.")
                        loginInProgress.value = false
                    }
                } else {
                    Log.d(TAG, "Login failed: ${task.exception?.localizedMessage}")
                    loginInProgress.value = false
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Login failed: ${exception.localizedMessage}")
                loginInProgress.value = false
            }
    }

    private fun fetchUserRoleAndNavigate(userId: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        userRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val role = document.getString("role")
                    when (role) {
                        "Admin" -> BarBerShopAppRoute.navigateTo(Screen.AdminHomeScreen)
                        "Customer" -> BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                        else -> BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                    }
                } else {
                    Log.d(TAG, "No such user document")
                }
                loginInProgress.value = false
            }
            .addOnFailureListener {
                Log.e(TAG, "Error fetching user role: ${it.localizedMessage}")
                loginInProgress.value = false
            }
    }


    private fun validateData() {
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )
        val mailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        allValidationsPassed.value = passwordResult.status && mailResult.status
        loginUIState.value = loginUIState.value.copy(
            passwordError = passwordResult.status,
            emailError = mailResult.status
        )
    }
}
