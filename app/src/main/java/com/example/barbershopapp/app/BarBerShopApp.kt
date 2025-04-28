package com.example.barbershopapp.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.barbershopapp.Screen.Admin.AdminHomeScreen
import com.example.barbershopapp.Screen.Admin.BookingManagementScreen
import com.example.barbershopapp.Screen.Admin.StylistManagementScreen
import com.example.barbershopapp.Screen.Admin.UserManagementScreen
import com.example.barbershopapp.Screen.Profile.ProfileScreen
import com.example.barbershopapp.Screen.History.HistoryScreen
import com.example.barbershopapp.Screen.Stylist.StylistScreen
import com.example.barbershopapp.Screen.Booking.BookScreen
import com.example.barbershopapp.Screen.User.MyApp
import com.example.barbershopapp.Screen.UserHomeScreen
import com.example.barbershopapp.auth.LoginScreen
import com.example.barbershopapp.auth.PolicyScreen
import com.example.barbershopapp.auth.RegisterScreen
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen

@Composable
fun BarBerShopApp() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = BarBerShopAppRoute.currentScreen.value, label = "") { currentState ->
            when (currentState) {
                is Screen.SliderScreen -> {
                    MyApp(
                        onRegisterClick = { BarBerShopAppRoute.currentScreen.value = Screen.RegisterScreen },
                        onLoginClick = { BarBerShopAppRoute.currentScreen.value = Screen.LoginScreen }
                    )
                }
                is Screen.RegisterScreen -> {
                    RegisterScreen()
                }
                is Screen.PolicyScreen -> {
                    PolicyScreen()
                }
                is Screen.LoginScreen -> {
                   LoginScreen()
                }
                is Screen.HomeScreen -> {
                    UserHomeScreen()
                }
                is Screen.ProfileScreen -> {
                    ProfileScreen()
                }
                is Screen.HistoryScreen -> {
                    HistoryScreen()
                }
                is Screen.StylistScreen -> {
                    StylistScreen()
                }
                is Screen.BookScreen -> {
                    BookScreen()
                }
                is Screen.AdminHomeScreen -> {
                    AdminHomeScreen()
                }
                is Screen.UserManagementScreen -> {
                    UserManagementScreen()
                }

                is Screen.StylistManagementScreen -> {
                    StylistManagementScreen()
                }

                is Screen.BookingManagementScreen -> {
                    BookingManagementScreen()
                }
            }
        }
    }
}