package com.example.barbershopapp.Screen.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershopapp.R
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.user.User
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.example.barbershopapp.ui.theme.fontFamily1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val authRepo : AuthRepo = AuthRepo()
    val userProfile by authRepo.user.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Profile") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Đẩy nội dung lên trên
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0388FC))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(painter = painterResource(id = R.drawable.img), contentDescription = "User Avatar",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userProfile?.name ?: "None",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Thông tin cá nhân", color = Color.Blue, fontFamily = fontFamily1, fontSize = 20.sp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProfileItem(iconRes = R.drawable.img_3, label = "Ưu đãi", icon2 = R.drawable.img_5, onClick = {BarBerShopAppRoute.navigateTo(Screen.HomeScreen)})
                ProfileItem(iconRes = R.drawable.img_4, label = "Lịch sử đơn hàng", icon2 = R.drawable.img_5, onClick = {BarBerShopAppRoute.navigateTo(Screen.HistoryScreen)})
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = userProfile?.name ?: "",
                    onValueChange = {  },
                    label = { Text("Họ và tên") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = userProfile?.phone.toString() ?: "",
                    onValueChange = {  },
                    label = { Text("Số điện thoại") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = userProfile?.email ?: "",
                    onValueChange = {  },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { authRepo.logout() },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Đăng xuất")
                }
            }
        }
    }
    BackButtonHandler {
        BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
    }
}

@Composable
fun ProfileItem(iconRes: Int, label: String, icon2: Int, onClick: ()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp)).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Sắp xếp các phần tử trong Row
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }

        Image(
            painter = painterResource(id = icon2), // Hiển thị icon thứ hai
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

