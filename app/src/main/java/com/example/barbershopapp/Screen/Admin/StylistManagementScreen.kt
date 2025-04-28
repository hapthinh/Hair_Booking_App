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
import com.example.barbershopapp.components.FormComponent
import com.example.barbershopapp.components.FormField
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.ItemCardComponent
import com.example.barbershopapp.components.MailTextFieldComponent
import com.example.barbershopapp.components.NovadiDatePickerDocked
import com.example.barbershopapp.components.NovadiMailTextFieldComponent
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.stylist.Stylist
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StylistManagementScreen() {
    val authRepo = AuthRepo()
    val stylists by authRepo.observeAllStylists().collectAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedStylist by remember { mutableStateOf<Stylist?>(null) }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeadingTextComponent(value = "Quản lý stylist")
        Button(onClick = {
            selectedStylist = null
            showDialog = true
            email = ""
            phone = ""
        }
        , modifier = Modifier.padding(vertical = 16.dp)
                .weight(0.1f)) { Text("Thêm stylist") }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
            ) {
            items(stylists) { stylist ->
                ItemCardComponent(
                    title = stylist.name ?: "N/A",
                    subtitle = stylist.email ?: "N/A",
                    details = listOf(
                        "Phone: ${stylist.phone ?: "N/A"}"
                    ),
                    onEditClick = {
                        selectedStylist = stylist
                        email = stylist.email ?: ""
                        phone = stylist.phone?.toString() ?: ""
                        showDialog = true
                    },
                    onDeleteClick = {
                        if (stylist.id != null) {
                            Log.d("StylistManagement", "Deleting stylist: ${stylist.id}")
                            authRepo.deleteStylist(stylist)
                        }
                    }
                )
            }
        }

        if (showDialog) {
            FormComponent(
                title = if (selectedStylist == null) "Thêm stylist" else "Chỉnh sửa stylist",
                fields = listOf(
                    FormField("name", "Name", selectedStylist?.name ?: ""),
                    FormField("phone", "Phone", selectedStylist?.phone?.toString() ?: "")
                ),
                content = {
                    Column {
                        NovadiMailTextFieldComponent(
                            labelValue = "Email",
                            painterResource = painterResource(id = R.drawable.mail),
                            text = email,  // Pass the current email
                            onTextSelected = { email = it }
                        )

                    }
                },
                onConfirm = { data ->
                    val stylist = Stylist(
                        id = selectedStylist?.id,
                        name = data["name"] ?: "",
                        email = email,
                        phone = data["phone"]?.toIntOrNull() ?: 0
                    )
                    if (selectedStylist == null) {
                        authRepo.addStylist(stylist)
                    } else {
                        authRepo.updateStylist(stylist)
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
        BackButtonHandler {
            BarBerShopAppRoute.navigateTo(Screen.AdminHomeScreen)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StylistManagementPreview() {
    StylistManagementScreen()
}