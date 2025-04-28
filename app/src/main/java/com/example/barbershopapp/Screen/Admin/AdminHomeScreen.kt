package com.example.barbershopapp.Screen.Admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.R
import com.example.barbershopapp.components.CustomTopBar
import com.example.barbershopapp.components.ServiceColumn
import com.example.barbershopapp.components.SppokyAppBottomNavigation
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.firebase.AuthViewModel
import com.example.barbershopapp.data.firebase.AuthViewModelFactory
import com.example.barbershopapp.data.firebase.CityUIState
import com.example.barbershopapp.data.firebase.StylistUiState
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen

val authRepo = AuthRepo()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceFlow() {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ServiceColumn(imageResource = R.drawable.ic_user_management, contentDescription = "Người Dùng",
            serviceName = "Người Dùng", onClick = {BarBerShopAppRoute.navigateTo(Screen.UserManagementScreen)})
        ServiceColumn(imageResource = R.drawable.ic_order_management, contentDescription = "Đơn Hàng",
            serviceName = "Đơn Hàng", onClick = {BarBerShopAppRoute.navigateTo(Screen.BookingManagementScreen)})
        ServiceColumn(imageResource = R.drawable.ic_voucher, contentDescription = "Voucher",
            serviceName = "Voucher", onClick = {})
        ServiceColumn(imageResource = R.drawable.ic_category_management, contentDescription = "Danh mục",
            serviceName = "Danh mục", onClick = {BarBerShopAppRoute.navigateTo(Screen.StylistManagementScreen)})
    }
}
@Composable
fun AdminHomeScreen(
    mockUserName: String = "Admin",
    isPreview: Boolean = false
) {
    val username = if (isPreview) mockUserName else {
        val authRepo = AuthRepo()
        val cityRepo = CityUIState()
        val stylistRepo = StylistUiState()
        val authViewModel: AuthViewModel = viewModel(
            factory = AuthViewModelFactory(authRepo, cityRepo, stylistRepo)
        )



    }

    Scaffold(
        bottomBar = {
            SppokyAppBottomNavigation()
        },
        topBar = {
            val authRepo1 : AuthRepo = AuthRepo()
            val userProfile by authRepo1.user.collectAsState(initial = null)
            CustomTopBar(username = userProfile?.name ?: "admin", onLogOutClick = {authRepo.logout()})
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            item {
                BodyContent()
            }
        }
    }
}
@Composable
fun BodyContent() {
    ServiceFlow()
}
@Preview(showBackground = true)
@Composable
fun AdminHomePreview() {
    AdminHomeScreen(isPreview = true)
}

