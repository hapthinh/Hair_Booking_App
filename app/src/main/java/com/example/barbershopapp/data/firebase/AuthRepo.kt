package com.example.barbershopapp.data.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.barbershopapp.data.booking.Booking
import com.example.barbershopapp.data.stylist.Stylist
import com.example.barbershopapp.data.user.User
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AuthRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val uid: String? = auth.currentUser?.uid
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers

    private val stylistCollection = firestore.collection("stylist")

    private val db = FirebaseFirestore.getInstance()
    private val bookingCollection = db.collection("bookings")
    private val _allBookings = MutableStateFlow<List<Booking>>(emptyList())
    val allBookings: Flow<List<Booking>> = _allBookings

    init {
        if (!uid.isNullOrEmpty()) {
            getUserData()
            observeAllUsers()
            observeAllStylists()
        }
    }

    private fun getUserData() {
        uid?.let {
            firestore.collection("users").document(it).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                _user.value = snapshot?.toObject(User::class.java)
            }
        }
    }

    fun observeAllUsers() {
        firestore.collection("users").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Error getting users: ", error)
                return@addSnapshotListener
            }
            val userList = snapshot?.documents?.mapNotNull { it.toObject(User::class.java) } ?: emptyList()
            _allUsers.value = userList
        }
    }


    fun setUser(newUser: User?) {
        _user.value = newUser
    }
    fun getCurrentUser() : User? {
        return _user.value
    }

    fun logout(){
        auth.signOut()
        BarBerShopAppRoute.navigateTo(Screen.LoginScreen)

    }

    fun checkForActiveSession() {
        if (auth.currentUser != null) {
            Log.d(TAG, "Valid session")
        } else {
            Log.d(TAG, "User is not logged in")
        }
    }

    fun getCurrentUserUid() : String? {
        return auth.currentUser?.uid
    }

    fun getAllUsers() {
        firestore.collection("users").get()
            .addOnSuccessListener { result ->
                val userList = result.documents.mapNotNull { it.toObject(User::class.java) }
                _allUsers.value = userList
            }
            .addOnFailureListener{ ex ->
                Log.e(TAG, "Error get all users: ", ex)
            }
    }

//    fun deleteUser(user: User) {
//        FirebaseFirestore.getInstance()
//            .collection("users")
//            .document(user.uid!!)
//            .delete()
//            .addOnSuccessListener {
//                Log.d("AuthRepo", "User deleted successfully")
//            }
//            .addOnFailureListener { exception ->
//                Log.e("AuthRepo", "Error deleting user: ${exception.message}")
//            }
//    }

    fun deleteUser(user: User) {
        val functions = FirebaseFunctions.getInstance()
        val url = "https://us-central1-barberapp-17c78.cloudfunctions.net/deleteUser?uid=${user.uid}"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    println("Success: ${response.body?.string()}")
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(user.uid!!)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("AuthRepo", "User deleted successfully from Firestore")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("AuthRepo", "Error deleting user from Firestore: ${exception.message}")
                        }
                } else {
                    println("Error: ${response.message}")
                }
            }
        })
    }



    fun addUser(user: User) {
        val userRef = firestore.collection("users").document()
        val userWithId = user.copy(uid = userRef.id)
        userRef.set(userWithId)
            .addOnSuccessListener {
                Log.d(TAG, "User successfully added!")
                getAllUsers()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user", e)
            }
    }



     fun saveUserToDatabase(user: User) {
        val database = FirebaseFirestore.getInstance()
        val userRef = user.uid?.let { database.collection("users").document(it) }

        val userMap = mapOf(
            "uid" to user.uid,
            "name" to user.name,
            "email" to user.email,
            "phone" to user.phone,
            "birthday" to user.birthday,
            "password" to user.password,
            "privacyPolicyAccepted" to user.privacyPolicyAccepted,
            "role" to user.role
        )

        userRef?.set(userMap)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User data saved successfully")
                } else {
                    Log.e(TAG, "Failed to save user data: ${task.exception?.message}")
                }
            }
            ?.addOnFailureListener { exception ->
                Log.e(TAG, "Failed to save user data: ${exception.message}")
            }
    }




    fun updateUser(user: User) {
        user.uid?.let { uid ->
            val userRef = firestore.collection("users").document(uid)
            userRef.set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "User updated")
                    getAllUsers()
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error updating user: ", exception)
                }
        } ?: run {
            Log.e(TAG, "Cannot update user: User UID is missing")
        }
    }

    fun observeAllStylists(): Flow<List<Stylist>> = callbackFlow {
        val stylistCollection = FirebaseFirestore.getInstance().collection("stylist")
        val listenerRegistration = stylistCollection.addSnapshotListener { snapshot, e ->
             if (e != null) {
                Log.w("AuthRepo", "Listen failed.", e)
                close(e)
                return@addSnapshotListener
            }
            val stylists = snapshot?.toObjects(Stylist::class.java) ?: emptyList()
            trySend(stylists)
        }
        awaitClose { listenerRegistration.remove() }
    }

    fun addStylist(stylist: Stylist) {
        try {
            val newStylistRef = stylistCollection.document()
            val stylistWithId = stylist.copy(id = newStylistRef.id)

            // Lưu stylist với id tự động
            newStylistRef.set(stylistWithId)
                .addOnSuccessListener {
                    Log.d("AuthRepo", "Stylist added successfully with ID: ${stylistWithId.id}")
                }
                .addOnFailureListener { e ->
                    Log.e("AuthRepo", "Error adding stylist", e)
                }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Exception in addStylist", e)
        }
    }

    fun filterBookings(userId: String, date: Date?, month: Int?) {
        // Implement your filtering logic here
        // You might need to query your database or filter the bookings list in memory
        _allBookings.value = _allBookings.value.filter { booking ->
            val userIdMatch = userId.isEmpty() || booking.userId.contains(userId)
            val dateMatch = date == null || SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(booking.date) == SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            val monthMatch = month == null || booking.date.toString().substring(5, 7).toInt() == month

            userIdMatch && dateMatch && monthMatch
        }
    }

    fun updateStylist(stylist: Stylist) {
        try {
            stylist.id?.let {
                stylistCollection.document(it.toString())
                    .set(stylist)
                    .addOnSuccessListener {
                        Log.d("AuthRepo", "Stylist updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("AuthRepo", "Error updating stylist", e)
                    }
            } ?: Log.e("AuthRepo", "Stylist ID is null")
        } catch (e: Exception) {
            Log.e("AuthRepo", "Exception in updateStylist", e)
        }
    }


    fun deleteStylist(stylist: Stylist) {
        try {
            stylist.id?.let {
                stylistCollection.document(it.toString()).delete()
                    .addOnSuccessListener {
                        Log.d("AuthRepo", "Stylist deleted successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("AuthRepo", "Error deleting stylist", e)
                    }
            } ?: Log.e("AuthRepo", "Stylist ID is null")
        } catch (e: Exception) {
            Log.e("AuthRepo", "Exception in deleteStylist", e)
        }
    }


    //BOOKING
    fun observeAllBookings(): Flow<List<Booking>> = callbackFlow {
        val listenerRegistration = bookingCollection.addSnapshotListener { snapshot, _ ->
            val bookings = snapshot?.documents?.mapNotNull { it.toObject(Booking::class.java) }
            trySend(bookings ?: emptyList())
        }
        awaitClose { listenerRegistration.remove() }
    }


    fun addBooking(booking: Booking) {
        bookingCollection.add(booking)
    }

     fun updateBooking(booking: Booking) {
        booking.bookingId.takeIf { it.isNotEmpty() }?.let {
            bookingCollection.document(it).set(booking)
        }
    }

     fun deleteBooking(booking: Booking) {
        booking.bookingId.takeIf { it.isNotEmpty() }?.let {
            bookingCollection.document(it).delete()
        }
    }

}
