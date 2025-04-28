package com.example.barbershopapp.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershopapp.R
import com.example.barbershopapp.Screen.User.ActionButtons
import com.example.barbershopapp.Screen.User.PageIndicator
import com.example.barbershopapp.data.home.BottomNavItem
import com.example.barbershopapp.data.stylist.Stylist
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.example.barbershopapp.ui.theme.BgColor
import com.example.barbershopapp.ui.theme.GrayColor
import com.example.barbershopapp.ui.theme.Primary
import com.example.barbershopapp.ui.theme.Secondary
import com.example.barbershopapp.ui.theme.Shape
import com.example.barbershopapp.ui.theme.TextColor
import com.example.barbershopapp.ui.theme.fontFamily1
import com.example.barbershopapp.ui.theme.fontFamily2
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import java.util.*
import androidx.compose.material3.Card as Card1

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        fontFamily = fontFamily1,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value, modifier = Modifier
            .fillMaxWidth()
            .heightIn(), style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
        ), color = TextColor, textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val textValue = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor

        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (Int?) -> Unit,
    errorStatus: Boolean = false
) {
    val textValue = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor

        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it.toIntOrNull()) // Chuyển đổi giá trị nhập vào thành Int? và truyền cho onTextSelected
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovadiMailTextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    text: String,  // Accept pre-filled text (email)
    onTextSelected: (String) -> Unit
) {
    val textValue = remember { mutableStateOf(text) }  // Initialize with the passed-in text
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,  // Show the current email
        onValueChange = {
            textValue.value = it
            onTextSelected(it)  // Update the parent component with the new email
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailTextFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val textValue = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor

        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovadiDatePickerDocked(
    labelValue: String,
    painterResource: Painter,
    selectedDate: Date?,  // Accept pre-selected date
    onDateChanged: (Date?) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    // Initialize selectedDate with the passed-in value (selectedDate)
    var currentSelectedDate by remember { mutableStateOf(selectedDate) }

    OutlinedTextField(
        value = currentSelectedDate?.let { convertMillisToDate(it.time) } ?: "",
        onValueChange = { },
        label = { Text(text = labelValue) },
        readOnly = true,
        leadingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    painter = painterResource,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { millis ->
                // Update the selected date
                currentSelectedDate = millis?.let { Date(it) }
                // Propagate the selected date to the parent
                onDateChanged(currentSelectedDate)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    labelValue: String,
    painterResource: Painter,
    onDateChanged: (Date?) -> Unit,
    errorStatus: Boolean = false
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it.time) } ?: "",
        onValueChange = { },
        label = { Text(text = labelValue) },
        readOnly = true,
        leadingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    painter = painterResource,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = !errorStatus
    )

    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = { millis ->
                selectedDate = millis?.let { Date(it) }
                onDateChanged(selectedDate)
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDateToYYMMDD(date: Date): String {
    val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordFieldComponent(
    labelValue: String, painterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.small),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val image = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }


            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(imageVector = image, description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun CheckboxComponent(
    value: String,
    onTextSelected: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckedChange.invoke(it)

            })

        ClickableTextComponent(value = value, onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "Bằng cách tiếp tục, bạn chấp nhận "
    val privacyPolicyText = "Chính sách Bảo mật"
    val andText = " và "
    val termsAndConditionsText = "Điều khoản Sử dụng"
    val lastText = " của chúng tôi"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(fontFamily = fontFamily2, color = Primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(fontFamily = fontFamily2, color = Primary)) {
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
        append(lastText)
    }

    ClickableText(text = annotatedString, onClick = { offset ->

        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")
                if ((span.item == privacyPolicyText) || (span.item == termsAndConditionsText)) {
                    onTextSelected(span.item)
                }

            }
    })
}

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled: Boolean = false) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = GrayColor,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = "hoặc",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextColor
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = GrayColor,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {
    val initialText =
        if (tryingToLogin) "Bạn đã có tài khoản? " else "Bạn chưa có tài khoản? "
    val loginText = if (tryingToLogin) "Đăng Nhập" else "Đăng Ký"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onTextSelected(span.item)
                    }
                }

        },
    )
}

@Composable
fun UnderLinedTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            color = Color.Gray
        ),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    toolbarTitle: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    onNavigationIconClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    fontSize = 22.sp,
                    text = toolbarTitle,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Primary,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = {
                    onNavigationIconClicked.invoke()
                }) {
                    navigationIcon()
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GrayColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String?,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    defaultDisplayText: String = "Chọn thành phố",
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf(defaultDisplayText) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue ?: displayText,
            onValueChange = {},
            label = { Text(text = label) },  // Hiển thị label "City"
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        displayText = option
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}


@Composable
fun StylistItemComponent(stylist: Stylist) {
    Card1(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tên: ${stylist.name}")
            Text(text = "Email: ${stylist.email}")
            Text(text = "Số điện thoại: ${stylist.phone}")
        }
    }
}

@Composable
fun SppokyAppBottomNavigation(
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Stylist,
        BottomNavItem.Booking,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
        tonalElevation = 8.dp
    ) {
        items.forEach { screenItem ->
            NavigationBarItem(
                icon = { Icon(screenItem.icon, contentDescription = screenItem.label) },
                label = { Text(screenItem.label, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                selected = BarBerShopAppRoute.currentScreen.value == screenItem.screen,
                onClick = {
                    BarBerShopAppRoute.currentScreen.value = screenItem.screen
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    username: String,
    onAvatarClick: () -> Unit = {},
    onLogOutClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { expanded = true }) {
                        Image(
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = "User Avatar",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = username,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }

                    //DropDown
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Đăng xuất") },
                            onClick = { expanded = false
                                onLogOutClick() })
                    }
                }
                IconButton(onClick = { /* Handle notification click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_1),
                        contentDescription = "Notification"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GrayColor
        ),
        modifier = Modifier.background(Color(0xFF1F4591)),
    )
}

@Composable
fun AutoUpdatingRatingBar() {
    var rating by remember { mutableStateOf(5f) }
    var increasing by remember { mutableStateOf(false) } // Determines whether rating is increasing or decreasing

    // Coroutine to update the rating automatically
    LaunchedEffect(Unit) {
        while (true) {
            delay(50) // Adjust delay for smoothness
            rating = if (increasing) {
                if (rating < 5f) {
                    rating + 0.1f // Increment rating
                } else {
                    // Change direction
                    increasing = false
                    rating - 0.1f
                }
            } else {
                if (rating > 1f) {
                    rating - 0.1f // Decrement rating
                } else {
                    // Change direction
                    increasing = true
                    rating + 0.1f
                }
            }
        }
    }

    RatingBar(
        value = rating,
        onValueChange = { newRating ->
            rating = newRating
        },
        onRatingChanged = { newRating ->
            // Handle rating change here if needed
        },
        style = RatingBarStyle.Fill()
    )
}

@Composable
fun ratingbody() {
    val colorStops = arrayOf(
        0.1f to Color(0xFF7AEFF7),
        0.4f to Color.White,
        0.7f to Color(0xFFF4D0FA),
        1f to Color(0xFF7AEFF7)
    )
    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(Brush.horizontalGradient(colorStops = colorStops))
            .clip(RoundedCornerShape(12.dp))
            .clickable { BarBerShopAppRoute.navigateTo(Screen.HistoryScreen) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.anhnu),
                contentDescription = "Custom Image",
                modifier = Modifier
                    .size(67.dp)
                    .padding(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "MỜI BẠN ĐÁNH GIÁ CHẤT LƯỢNG PHỤC VỤ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = fontFamily1,
                )
                Text(
                    text = "Phản hồi của bạn giúp chúng tôi cải thiện dịch vụ",
                    fontSize = 14.sp,
                    fontFamily = fontFamily2
                )
                AutoUpdatingRatingBar()
            }
        }
    }
}

//@Composable
//fun discount() {
//    Box(
//        modifier = Modifier
//            .height(140.dp)
//            .fillMaxWidth()
//            .padding(8.dp)
//            .background(Color.White)
//            .clip(RoundedCornerShape(12.dp))
//            .clickable { BarBerShopAppRoute.navigateTo(Screen.HistoryScreen) }
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(end = 8.dp)
//                .align(Alignment.TopStart),
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.voucher2),
//                contentDescription = "Custom Image",
//                modifier = Modifier.fillMaxSize()
//                    .padding(1.dp)
//            )
//        }
//    }
//}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerSection(pagerItems: List<PagerItem>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) { currentPage ->
        val pagerItem = pagerItems[currentPage]

        Image(
            painter = painterResource(id = pagerItem.imageResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(360.dp)
                .fillMaxWidth()
        )
    }
}

data class PagerItem(
    val imageResId: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun discount(
    modifier: Modifier = Modifier
) {
    val pagerItems = listOf(
        PagerItem(R.drawable.a1),
        PagerItem(R.drawable.a2),
        PagerItem(R.drawable.a3)
    )

    val pagerState = rememberPagerState(
        pageCount = { pagerItems.size }
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPagerSection(pagerItems, pagerState)
        PageIndicator(
            pageCount = pagerItems.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ServiceColumn(
    imageResource: Int,
    contentDescription: String,
    serviceName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .shadow(
                3.dp,
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick) // Thêm sự kiện onClick vào đây
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            modifier = Modifier.size(130.dp) // Sửa modifier này thành Modifier riêng cho Image
        )
        Text(text = serviceName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Blue)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NDatePickerDocked(
    labelValue: String,
    onDateChanged: (Date) -> Unit,
    errorStatus: Boolean
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Biến để lưu trữ ngày đã chọn
    var selectedDate by remember { mutableStateOf(calendar.time) }

    // Định dạng ngày hiển thị
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Hiển thị DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
            onDateChanged(selectedDate) // Gọi lại khi người dùng chọn ngày
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Hiển thị giao diện
    Row {
        Button(onClick = { datePickerDialog.show() }) {
            Text(text = labelValue)
        }
        Text(text = dateFormat.format(selectedDate)) // Hiển thị ngày đã chọn
    }

    // Có thể thêm xử lý lỗi dựa trên errorStatus nếu cần
    if (errorStatus) {
        Text(text = "Error: Please select a valid date", color = androidx.compose.ui.graphics.Color.Red)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormComponent(
    title: String,
    fields: List<FormField>,
    content: @Composable (() -> Unit)? = null,
    onConfirm: (Map<String, String>) -> Unit,
    onDismiss: () -> Unit
) {
    val formData = remember {
        mutableStateOf(fields.associate { it.key to it.value })
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp) // Thêm khoảng cách dưới tiêu đề
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Thêm padding xung quanh nội dung
                    .background(MaterialTheme.colorScheme.background) // Thêm background màu nền
            ) {
                fields.forEach { field ->
                    TextField(
                        value = formData.value[field.key] ?: "",
                        onValueChange = { formData.value = formData.value.toMutableMap().apply { put(field.key, it) } },
                        label = { Text(field.label) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .width(IntrinsicSize.Min),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
                            cursorColor = MaterialTheme.colorScheme.primary,
                            errorCursorColor = MaterialTheme.colorScheme.error,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
                        ),
                        shape = MaterialTheme.shapes.small,
                        maxLines = 1,
                        singleLine = true // Đảm bảo chỉ có một dòng hiển thị
                    )
                }
                content?.let {
                    Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách trước phần content tùy chỉnh
                    it()
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(formData.value) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Cancel")
            }
        },
        shape = MaterialTheme.shapes.medium, // Thêm góc bo cho AlertDialog
        containerColor = MaterialTheme.colorScheme.surface, // Đặt màu nền cho AlertDialog
        tonalElevation = 8.dp // Thêm độ nổi cho AlertDialog
    )
}

data class FormField(val key: String, val label: String, val value: String = "")

@Composable
fun ItemCardComponent(
    title: String,
    subtitle: String,
    details: List<String>,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Card1(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = CircleShape)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
                details.forEach{
                    detail -> Text(text = detail, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Chỉnh Sửa")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Xóa")
                }
            }
        }
    }
}
@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onFilterApplied: (userId: String, date: String, month: String) -> Unit
) {
    var filterBy by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Date?>(null) }
    var selectedMonth by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier.fillMaxWidth(),
        title = { Text("Lọc đơn đặt") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Lọc theo:")
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf("Người dùng", "Ngày", "Tháng")) { option ->
                        OutlinedButton(
                            onClick = { filterBy = option },
                            modifier = Modifier
                                .height(48.dp)
                        ) {
                            Text(
                                text = option,
                                maxLines = 1,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                when (filterBy) {
                    "Người dùng" -> {
                        TextField(
                            value = userId,
                            onValueChange = { userId = it },
                            label = { Text("Nhập ID người dùng") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    "Ngày" -> {
                        DatePickerDocked(
                            labelValue = "Chọn ngày",
                            painterResource = painterResource(id = R.drawable.date),
                            onDateChanged = { date -> selectedDate = date },
                            errorStatus = false
                        )
                    }
                    "Tháng" -> {
                        TextField(
                            value = selectedMonth,
                            onValueChange = { selectedMonth = it },
                            label = { Text("MM-YY (ví dụ: 08-24)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val formattedDate = selectedDate?.let {
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                    } ?: ""

                    onFilterApplied(userId, formattedDate, selectedMonth)
                }
            ) {
                Text("Áp dụng")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Hủy")
            }
        }
    )
}
