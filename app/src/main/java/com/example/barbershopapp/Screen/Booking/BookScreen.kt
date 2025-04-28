package com.example.barbershopapp.Screen.Booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.R
import com.example.barbershopapp.components.AppToolbar
import com.example.barbershopapp.components.ButtonComponent
import com.example.barbershopapp.components.DatePickerDocked
import com.example.barbershopapp.components.DynamicSelectTextField
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.firebase.AuthViewModel
import com.example.barbershopapp.data.firebase.AuthViewModelFactory
import com.example.barbershopapp.data.firebase.CityUIState
import com.example.barbershopapp.data.firebase.StylistUiState
import java.util.Date
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookScreen() {
    val authRepo = AuthRepo()
    val cityRepo = CityUIState()
    val stylistRepo = StylistUiState()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepo, cityRepo,stylistRepo)
    )

    val workingHours = listOf(
        "08:00 AM - 12:00 PM (Buổi Sáng)",
        "01:00 PM - 05:00 PM (Buổi Tối)",
    )

    val cityList by authViewModel.cities.collectAsState()
    val stylistList by authViewModel.stylist.collectAsState()
    val currentUser by authViewModel.user.collectAsState()

    var selectedCityName by remember { mutableStateOf<String?>(null) }
    var selectedAddress by remember { mutableStateOf<String?>(null) }
    var selectedStylistName by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var selectedHour by remember { mutableStateOf<String?>(null) }
    var voucherCode by remember { mutableStateOf<String>("") }


    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = stringResource(R.string.booking),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.House,
                        contentDescription = stringResource(R.string.menu),
                        tint = Color.Black
                    )
                },
                onNavigationIconClicked = {
                    BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                },
                actions = {
                    IconButton(onClick = {
                        BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = stringResource(R.string.logout),
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)) {

                HeaderText(text = stringResource(id = R.string.select_city_header))

                DynamicSelectTextField(
                    selectedValue = selectedCityName,
                    options = cityList.map {it.name},
                    label = stringResource(id = R.string.city),
                    onValueChangedEvent = { newCityName ->
                        selectedCityName = newCityName
                        selectedAddress = null
                    }
                )

                selectedCityName?.let { selectedCityName ->
                    val selectedCity = cityList.find { it.name == selectedCityName }
                    selectedCity?.let { city ->
                        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                            items(city.addresses) { address ->
                                AddressBox(
                                    address = address,
                                    isSelected = selectedAddress == address,
                                    onClick = {
                                        selectedAddress = address
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                HeaderText(text = stringResource(id = R.string.select_stylist_header))
                DynamicSelectTextField(
                    selectedValue = selectedStylistName,
                    options = stylistList.mapNotNull { it.name },
                    label = stringResource(id = R.string.stylist),
                    onValueChangedEvent = { newStylistName ->
                        selectedStylistName = newStylistName
                    },
                    defaultDisplayText = "Chọn stylist"
                )

                Spacer(modifier = Modifier.height(18.dp))
                HeaderText(text = stringResource(id = R.string.select_date_header))
                DatePickerDocked(
                    labelValue = stringResource(id = R.string.date_booking),
                    painterResource = painterResource(id = R.drawable.date),
                    onDateChanged = { date ->
                        selectedDate = date
                    },
                    errorStatus = selectedDate == null
                )

                Spacer(modifier = Modifier.height(18.dp))
                HeaderText(text = stringResource(id = R.string.select_hour_header))
                DynamicSelectTextField(
                    selectedValue = selectedHour,
                    options = workingHours,
                    label = stringResource(id = R.string.select_hour),
                    onValueChangedEvent = { newHour ->
                        selectedHour = newHour
                    },
                    defaultDisplayText = "Chọn giờ"
                )

                Spacer(modifier = Modifier.height(18.dp))
                HeaderText(text = stringResource(id = R.string.input_voucher_header))
                TextField(
                    value = voucherCode,
                    onValueChange = { newVoucherCode ->
                        voucherCode = newVoucherCode
                    },
                    label = { Text(stringResource(id = R.string.voucher_code)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                ButtonComponent(
                    value = "Đặt lịch",
                    onButtonClicked = {
                        if (selectedCityName != null && selectedAddress != null && selectedStylistName != null && selectedDate != null && selectedHour != null) {
                            saveBookingToFirebase(
                                cityName = selectedCityName!!,
                                address = selectedAddress!!,
                                stylistName = selectedStylistName!!,
                                date = selectedDate!!,
                                hours = selectedHour!!,
                                voucher = voucherCode
                            )
                        } else {
                            Log.e("Booking", "Please select all fields")
                        }
                    },
                    isEnabled = selectedCityName != null && selectedAddress != null && selectedStylistName != null && selectedDate != null && selectedHour != null
                )
            }
        }
        BackButtonHandler {
            BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
        }
    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

private fun saveBookingToFirebase(voucher : String, cityName: String, address: String, stylistName: String, date: Date, hours : String) {
    val database = FirebaseFirestore.getInstance()
    val bookingRef = database.collection("bookings").document()

    val authRepo = AuthRepo()
    val uid = authRepo.getCurrentUserUid() ?: return

    val booking = mapOf(
        "userId" to uid,
        "bookingId" to bookingRef.id,
        "cityName" to cityName,
        "address" to address,
        "stylistName" to stylistName,
        "date" to date.toString(),
        "hours" to hours,
        "voucher" to voucher
    )

    bookingRef.set(booking)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Booking", "Booking saved successfully")
                BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
            } else {
                Log.e("Booking", "Failed to save booking: ${task.exception?.message}")
            }
        }
        .addOnFailureListener { exception ->
            Log.e("Booking", "Failed to save booking: ${exception.message}")
        }

}

@Composable
fun AddressBox(
    address: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(if (isSelected) Color.Blue else Color.Gray)
            .clickable(onClick = onClick),
        color = Color.LightGray
    ) {
        Text(
            text = address,
            modifier = Modifier.padding(16.dp),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}