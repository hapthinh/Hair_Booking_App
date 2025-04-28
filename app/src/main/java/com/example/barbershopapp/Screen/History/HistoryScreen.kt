package com.example.barbershopapp.Screen.History

import android.transition.Scene
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.components.ButtonComponent
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.data.booking.Booking
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun HistoryScreen(){
    val authRepo = AuthRepo()
    val currentUserUid = authRepo.getCurrentUserUid()
    val firestore = FirebaseFirestore.getInstance()
    var bookingList by remember {
        mutableStateOf<List<Booking>>(emptyList())
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = "LỊCH SỬ ĐẶT LỊCH")
        Spacer(modifier = Modifier.height(20.dp))
        LaunchedEffect (currentUserUid) {
            currentUserUid?.let { uid ->
                firestore.collection("bookings")
                    .whereEqualTo("userId", uid)
                    .addSnapshotListener{ snapshot, error ->
                        if(error != null) {
                            Log.e("HistoryScreen","Error fetch")
                            return@addSnapshotListener
                        }
                        snapshot?.let{
                            val bookings = it.toObjects(Booking::class.java)
                            bookingList = bookings
                        }
                    }
            }
        }
        BookingsList(bookingsList = bookingList)
        BackButtonHandler {
            BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
        }
    }

}

@Composable
fun BookingsList(bookingsList: List<Booking>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(bookingsList) { booking ->
            BookingItem(booking = booking)
        }
    }
}

@Composable
fun BookingItem(booking: Booking) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "#${booking.bookingId}",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
                )
            Text(
                text = "Địa chi chinh nhánh: ${booking.address}",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Thời gian: ${booking.hours} - ${booking.date}",
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ButtonComponent(value = "Hủy lịch", onButtonClicked = {cancelBooking(booking) },isEnabled = true)

        }
    }

}

fun cancelBooking(booking: Booking) {
    val firestore = FirebaseFirestore.getInstance()

    // Định dạng ngày trong dữ liệu
    val dateFormatInput = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

    // Parse ngày đặt từ chuỗi ngày lưu trong booking
    val bookingDate: Date? = try {
        booking.date?.let { dateFormatInput.parse(it.toString()) }
    } catch (e: ParseException) {
        Log.e("Booking", "Date parsing failed", e)
        null
    }

    if (bookingDate != null) {
        val currentTime = Calendar.getInstance().time

        // Tính toán khoảng cách thời gian giữa ngày đặt và thời gian hiện tại
        val diff = bookingDate.time - currentTime.time
        val daysDiff = diff / (1000 * 60 * 60 * 24) // chuyển đổi milliseconds sang ngày

        if (daysDiff > 1) {
            // Nếu ngày đặt cách thời gian hiện tại hơn 1 ngày, cho phép hủy
            firestore.collection("bookings").document(booking.bookingId).delete()
                .addOnSuccessListener {
                    Log.d("Booking", "Booking canceled successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Booking", "Failed to cancel booking", e)
                }
        } else {
            Log.d("Booking", "Cannot cancel booking within 1 day of the appointment")
        }
    } else {
        Log.e("Booking", "Invalid booking date")
    }
}

//fun cancelBooking(booking: Booking){
//    val firestore = FirebaseFirestore.getInstance()
//    firestore.collection("bookings").document(booking.bookingId).delete()
//        .addOnSuccessListener {
//            Log.d("Booking", "Booking cancel successfully")
//        }
//        .addOnFailureListener {
//            e -> Log.e("Booking","Failed")
//        }
//}
