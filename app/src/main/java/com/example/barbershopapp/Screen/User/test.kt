package com.example.barbershopapp.Screen.User

import android.annotation.SuppressLint
import android.media.Rating
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            var rating by remember { mutableStateOf(5f) }

            RatingBar(
                value = rating,
                onValueChange = { newRating ->
                    rating = newRating
                },
                onRatingChanged = { newRating ->
                    Log.d("TAG", "onRatingChanged: $newRating")
                },
                style = RatingBarStyle.Fill()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}