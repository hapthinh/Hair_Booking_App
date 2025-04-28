package com.example.barbershopapp.auth

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.R
import com.example.barbershopapp.components.ButtonComponent
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.example.barbershopapp.ui.theme.fontFamily1

@Composable
fun PolicyText(title: String, content: String) {
    val titleStyle = TextStyle(
        fontFamily = fontFamily1,
        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
        color = MaterialTheme.colorScheme.primary
    )

    val contentStyle = TextStyle(
        fontFamily = fontFamily1,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(color = colorResource(id = R.color.teal_200))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = titleStyle,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = content,
            style = contentStyle
        )
    }
}

@Composable
fun PolicyScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Column {
            HeadingTextComponent(value = stringResource(id = R.string.title_policy))
            Spacer(modifier = Modifier.height(16.dp))

            PolicyText(
                title = stringResource(id = R.string.policy_title_1),
                content = stringResource(id = R.string.policy_paragraph_1)
            )

            PolicyText(
                title = stringResource(id = R.string.policy_title_2),
                content = stringResource(id = R.string.policy_paragraph_2)
            )

            PolicyText(
                title = stringResource(id = R.string.policy_title_3),
                content = stringResource(id = R.string.policy_paragraph_3)
            )

            PolicyText(
                title = stringResource(id = R.string.policy_title_4),
                content = stringResource(id = R.string.policy_paragraph_4)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    BackButtonHandler {
        BarBerShopAppRoute.navigateTo(Screen.RegisterScreen)
    }
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    PolicyScreen()
}