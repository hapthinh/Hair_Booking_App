package com.example.barbershopapp.data.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.barbershopapp.navigation.Screen

sealed class BottomNavItem(val screen : Screen, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(Screen.HomeScreen, Icons.Default.Home, "Home")
    object Booking : BottomNavItem(Screen.BookScreen, Icons.Default.Timer, "Booking")
    object Profile : BottomNavItem(Screen.ProfileScreen, Icons.Default.Person, "Profile")
    object Stylist : BottomNavItem(Screen.StylistScreen, Icons.Default.PermIdentity,"Barber")
    object History : BottomNavItem(Screen.HistoryScreen, Icons.Default.History, "history")
}