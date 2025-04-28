package com.example.barbershopapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.barbershopapp.R

// Cấu hình provider cho Google Fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Định nghĩa fontName1 và fontFamily1
val fontName1 = GoogleFont("Arial")
val fontFamily1 = FontFamily(
    Font(
        googleFont = fontName1,
        fontProvider = provider,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

// Định nghĩa fontName2 và fontFamily2
val fontName2 = GoogleFont("Roboto")
val fontFamily2 = FontFamily(
    Font(
        googleFont = fontName2,
        fontProvider = provider,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    )
)

// Định nghĩa Typography tùy chỉnh
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = fontFamily1, // Sử dụng fontFamily1
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
)
