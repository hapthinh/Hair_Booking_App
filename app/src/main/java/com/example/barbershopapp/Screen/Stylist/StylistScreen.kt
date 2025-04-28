package com.example.barbershopapp.Screen.Stylist

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.data.stylist.Stylist
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.example.barbershopapp.ui.theme.Primary
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun StylistScreen() {
    val firestore = FirebaseFirestore.getInstance()
    var stylistList by remember { mutableStateOf<List<Stylist>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize()) {
        HeadingTextComponent(value = "DANH SÁCH THỢ CẮT TÓC")
        Spacer(modifier = Modifier.height(20.dp))

        LaunchedEffect(Unit) {
            firestore.collection("stylist")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("StylistScreen", "Error fetching stylists", error)
                        return@addSnapshotListener
                    }
                    snapshot?.let {
                        val stylists = it.toObjects(Stylist::class.java)
                        stylistList = stylists
                    }
                }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(stylistList) { stylist ->
                StylistItemComponent(stylist = stylist)
            }
        }
    }
}

@Composable
fun StylistItemComponent(stylist: Stylist) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Primary
        ),
        modifier = Modifier
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tên: ${stylist.name}", style = MaterialTheme.typography.headlineMedium)

            if (expanded) {
                Text(text = "Email: ${stylist.email}",style = MaterialTheme.typography.bodyMedium)
                Text(text = "Số điện thoại: ${stylist.phone}",style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "- Thân thiện tận tình chăm sóc khách hàng. "
                )
                Text(
                    text = "- Với phương châm khách hàng là thượng đế tôi xin cam kết làm tốt nhiệm vụ của mình. "
                )
            }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Show less" else "Show more"
                )
            }
        }
        BackButtonHandler {
            BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
        }
    }
}