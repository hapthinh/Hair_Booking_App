package com.example.barbershopapp.Screen.Admin

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.R
import com.example.barbershopapp.components.DatePickerDocked
import com.example.barbershopapp.components.FilterDialog
import com.example.barbershopapp.components.FormComponent
import com.example.barbershopapp.components.FormField
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.ItemCardComponent
import com.example.barbershopapp.data.booking.Booking
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BookingManagementScreen() {
    val authRepo = AuthRepo()
    val bookings by authRepo.observeAllBookings().collectAsState(initial = emptyList())
    var filteredBookings by remember { mutableStateOf(bookings) }
    var showDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedBooking by remember { mutableStateOf<Booking?>(null) }
    var bookingDate by remember { mutableStateOf<Date?>(null) }
    var userId by remember { mutableStateOf("") }


    LaunchedEffect(bookings) {
        filteredBookings = bookings
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = "Quản lý đơn hàng")

        Button(onClick = { showDialog = true }) {
            Text("Lọc")
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filteredBookings) { booking ->
                ItemCardComponent(
                    title = booking.cityName,
                    subtitle = "Stylist: ${booking.stylistName}",
                    details = listOf(
                        "User ID: ${booking.userId}",
                        "Date: ${booking.date}",
                        "Hours: ${booking.hours}",
                        "Address: ${booking.address}"
                    ),
                    onEditClick = {
                        selectedBooking = booking
                        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.getDefault())
                        val parsedDate = dateFormat.parse(booking.date)
                        bookingDate = parsedDate
                        userId = booking.userId
                        showEditDialog = true
                    },
                    onDeleteClick = {
                        Log.d("BookingManagement", "Deleting booking: ${booking.bookingId}")
                        authRepo.deleteBooking(booking)
                    }
                )
            }
        }

        if (showDialog) {
            FilterDialog(
                onDismiss = { showDialog = false },
                onFilterApplied = { filterUserId, date, month ->
                    filteredBookings = bookings.filter { booking ->
                        val dateFormatInput = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)  // For parsing input
                        val dateFormatOutput = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // For formatting output

                        val parsedDate = try {
                            booking.date?.let { dateFormatInput.parse(it.toString()) }
                        } catch (e: ParseException) {
                            null
                        }
                        val formattedDate = parsedDate?.let { dateFormatOutput.format(it) } ?: "Invalid Date"  // Format to MM-YY
                        val bookingDate = formattedDate

                        val dateMMYYFormatOutput = SimpleDateFormat("MM-yy", Locale.getDefault())  // For formatting output as MM-YY
                        val parsedDate1 = try {
                            booking.date?.let { dateFormatInput.parse(it.toString()) }
                        } catch (e: ParseException) {
                            null
                        }
                        val formattedMYDate = parsedDate1?.let { dateMMYYFormatOutput.format(it) } ?: "Invalid Date"
                        val bookingMYDate = formattedMYDate
                        (filterUserId.isEmpty() || booking.userId == filterUserId) &&
                                (date.isEmpty() || bookingDate.contains(date)) &&
                                (month.isEmpty() || bookingMYDate.contains(month))
                    }
                    showDialog = false
                }
            )
        }
        if (showEditDialog) {
            FormComponent(
                title = if (selectedBooking == null) "Thêm đơn hàng" else "Chỉnh sửa đơn hàng",
                fields = listOf(
                    FormField("cityName", "Tên thành phố", selectedBooking?.cityName ?: ""),
                    FormField("address", "Địa chỉ", selectedBooking?.address ?: ""),
                    FormField("stylistName", "Tên stylist", selectedBooking?.stylistName ?: ""),
                    FormField("hours", "Giờ", selectedBooking?.hours ?: "")
                ),
                content = {
                    Column {
                        DatePickerDocked(
                            labelValue = "Ngày",
                            painterResource = painterResource(id = R.drawable.date),
                            onDateChanged = { date ->
                                bookingDate = date
                            },
                            errorStatus = false
                        )
                    }
                },
                onConfirm = { data ->

                    val booking = Booking(
                        bookingId = selectedBooking?.bookingId ?: "",
                        userId = userId,
                        cityName = data["cityName"] ?: "",
                        address = data["address"] ?: "",
                        stylistName = data["stylistName"] ?: "",
                        date = bookingDate?.toString() ?: "",
                        hours = data["hours"] ?: ""
                    )
                    if (selectedBooking == null) {
                        authRepo.addBooking(booking)
                    } else {
                        authRepo.updateBooking(booking)
                    }
                    showEditDialog = false
                },
                onDismiss = { showEditDialog = false }
            )
        }

        BackButtonHandler {
            BarBerShopAppRoute.navigateTo(Screen.AdminHomeScreen)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookingManagementPreview() {
    BookingManagementScreen()
}




