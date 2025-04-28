package com.example.barbershopapp.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.height
import com.example.barbershopapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.components.CustomTopBar
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.ServiceColumn
import com.example.barbershopapp.components.SppokyAppBottomNavigation
import com.example.barbershopapp.components.discount
import com.example.barbershopapp.components.ratingbody
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.firebase.AuthViewModel
import com.example.barbershopapp.data.firebase.AuthViewModelFactory
import com.example.barbershopapp.data.firebase.CityUIState
import com.example.barbershopapp.data.firebase.StylistUiState


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceFlow() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ServiceColumn(imageResource = R.drawable.cattoc, contentDescription = "Cắt tóc", serviceName = "Cắt tóc", onClick = {})
        ServiceColumn(imageResource = R.drawable.uontoc, contentDescription = "Uốn tóc", serviceName = "Uốn tóc", onClick = {})
        ServiceColumn(imageResource = R.drawable.duoitoc, contentDescription = "Duỗi tóc", serviceName = "Duỗi tóc", onClick = {})
        ServiceColumn(imageResource = R.drawable.goidau, contentDescription = "Gội đầu", serviceName = "Gội đầu", onClick = {})
    }
}
@Composable
fun BodyContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.rating))
        ratingbody()
        Spacer(modifier = Modifier.height(16.dp))
        HeadingTextComponent(value = stringResource(id = R.string.coupon))
        discount()
        Spacer(modifier = Modifier.height(16.dp))
        HeadingTextComponent(value = stringResource(id = R.string.service))
        ServiceFlow()
    }
}

@Composable
fun UserHomeScreen() {
    val authRepo = AuthRepo()
    val cityRepo = CityUIState()
    val stylistRepo = StylistUiState()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepo, cityRepo, stylistRepo)
    )
    val authRepo1 : AuthRepo = AuthRepo()
    val userProfile by authRepo1.user.collectAsState(initial = null)


    Scaffold(
        bottomBar = {
            SppokyAppBottomNavigation()
        },
        topBar = {
            CustomTopBar(username = userProfile?.name ?: "GUEST", onLogOutClick = {authRepo.logout()})
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

@Preview(showBackground = true)
@Composable
fun BodyPreview(){
    BodyContent()
}