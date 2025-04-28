package com.example.barbershopapp.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.R
import com.example.barbershopapp.components.ButtonComponent
import com.example.barbershopapp.components.CheckboxComponent
import com.example.barbershopapp.components.ClickableLoginTextComponent
import com.example.barbershopapp.components.DatePickerDocked
import com.example.barbershopapp.components.DividerTextComponent
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.MailTextFieldComponent
import com.example.barbershopapp.components.MyTextFieldComponent
import com.example.barbershopapp.components.NormalTextComponent
import com.example.barbershopapp.components.PasswordFieldComponent
import com.example.barbershopapp.components.PhoneFieldComponent
import com.example.barbershopapp.data.register.RegisterUIEvent
import com.example.barbershopapp.data.register.RegisterViewModel
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import java.util.Date

@Composable
fun RegisterScreen(
    RegisterViewModel: RegisterViewModel = viewModel()

) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                HeadingTextComponent(value = stringResource(id = R.string.hello))
                Spacer(modifier = Modifier.height(12.dp))
                NormalTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.person),
                    onTextSelected = {
                        RegisterViewModel.onEvent(RegisterUIEvent.NameChanged(it))
                    },
                    errorStatus = RegisterViewModel.registrationUIState.value.nameError
                )
                MailTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.mail),
                    onTextSelected = {
                        RegisterViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = RegisterViewModel.registrationUIState.value.emailError
                )
                PhoneFieldComponent(
                    labelValue = stringResource(id = R.string.phone),
                    painterResource = painterResource(id = R.drawable.phone),
                    onTextSelected = {
                        RegisterViewModel.onEvent(RegisterUIEvent.PhoneChanged(it ?: 0))
                    },
                    errorStatus = RegisterViewModel.registrationUIState.value.phoneError
                )
                DatePickerDocked(
                    labelValue = stringResource(id = R.string.bỉthday),
                    painterResource = painterResource(id = R.drawable.date),
                    onDateChanged = { date ->
                        RegisterViewModel.onEvent(RegisterUIEvent.DateChanged(date ?: Date()))
                    },
                    errorStatus = RegisterViewModel.registrationUIState.value.dateError
                )
                PasswordFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        RegisterViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    errorStatus = RegisterViewModel.registrationUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(12.dp))
                CheckboxComponent(
                    value = stringResource(id = R.string.policy),
                    onTextSelected = {
                        BarBerShopAppRoute.navigateTo(Screen.PolicyScreen)
                    }, onCheckedChange = {
                        RegisterViewModel.onEvent(RegisterUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )
                Spacer(modifier = Modifier.height(70.dp))
                ButtonComponent(value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        RegisterViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                },
                    isEnabled = RegisterViewModel.allValidationsPassed.value)
                Spacer(modifier = Modifier.height(20.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))
                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    BarBerShopAppRoute.navigateTo(Screen.LoginScreen)
                })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}