package com.example.barbershopapp.Screen.Admin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.R
import com.example.barbershopapp.components.DatePickerDocked
import com.example.barbershopapp.components.FormComponent
import com.example.barbershopapp.components.FormField
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.ItemCardComponent
import com.example.barbershopapp.components.MailTextFieldComponent
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.user.User
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.barbershopapp.components.NovadiDatePickerDocked
import com.example.barbershopapp.components.NovadiMailTextFieldComponent
import com.example.barbershopapp.navigation.BackButtonHandler
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserManagementScreen() {
    val authRepo = AuthRepo()
    val users by authRepo.allUsers.collectAsState()
    var birthDate by remember { mutableStateOf<Date?>(null) }
    var email by remember { mutableStateOf("") }
    var showAddUserDialog by remember { mutableStateOf(false) }
    var showEditUserDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    // State for delete confirmation dialog
    var showConfirmDialog by remember { mutableStateOf(false) }
    var selectedUserForDeletion by remember { mutableStateOf<User?>(null) }

    // Adding role state for dropdown menu
    var selectedRole by remember { mutableStateOf("Customer") }

    // Date format for display and Firebase storage
    val displayDateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val firebaseDateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        authRepo.observeAllUsers()
    }

    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                HeadingTextComponent(value = "Quản lý người dùng")
                Button(onClick = {
                    showAddUserDialog = true
                    birthDate = null
                    email = ""
                    selectedRole = "Customer"
                }) { Text("Thêm người dùng") }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(users) { user ->
                        val formattedDate = user.birthday?.let {
                            try {
                                val parsedDate = firebaseDateFormat.parse(it)
                                parsedDate?.let { date -> displayDateFormat.format(date) } ?: "N/A"
                            } catch (e: ParseException) {
                                "N/A"
                            }
                        } ?: "N/A"

                        ItemCardComponent(
                            title = user.name ?: "N/A",
                            subtitle = user.email ?: "N/A",
                            details = listOf(
                                "Phone: ${user.phone ?: "N/A"}",
                                "Birthdate: $formattedDate",
                                "Role: ${user.role ?: "N/A"}"
                            ),
                            onEditClick = {
                                selectedUser = user
                                birthDate = try {
                                    user.birthday?.let { firebaseDateFormat.parse(it) }
                                } catch (e: ParseException) {
                                    null
                                }
                                email = user.email ?: ""
                                selectedRole = user.role ?: "Customer"
                                showEditUserDialog = true
                            },
                            onDeleteClick = {
                                selectedUserForDeletion = user
                                showConfirmDialog = true
                            }
                        )
                    }
                }

                if (showConfirmDialog) {
                    AlertDialog(
                        onDismissRequest = { showConfirmDialog = false },
                        title = { Text("Xác nhận xóa") },
                        text = { Text("Bạn có chắc chắn muốn xóa người dùng này không?") },
                        confirmButton = {
                            Button(onClick = {
                                selectedUserForDeletion?.let { authRepo.deleteUser(it) }
                                showConfirmDialog = false
                            }) {
                                Text("Xác nhận")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showConfirmDialog = false }) {
                                Text("Hủy")
                            }
                        }
                    )
                }


                if (showAddUserDialog) {
                    FormComponent(
                        title = "Thêm người dùng",
                        fields = listOf(
                            FormField("name", "Name", ""),
                            FormField("phone", "Phone", ""),
                            FormField("password", "Password", ""),
                            FormField("email", "Email", ""),
                        ),
                        content = {
                            Column {
                                NovadiDatePickerDocked(
                                    labelValue = "Ngày sinh",
                                    painterResource = painterResource(id = R.drawable.date),
                                    selectedDate = birthDate,
                                    onDateChanged = { date ->
                                        birthDate = date
                                    }
                                )

                                var expanded by remember { mutableStateOf(false) }
                                Button(onClick = { expanded = true }) {
                                    Text(text = selectedRole)
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        selectedRole = "Customer"
                                        expanded = false
                                    }) {
                                        Text("Customer")
                                    }
                                    DropdownMenuItem(onClick = {
                                        selectedRole = "Admin"
                                        expanded = false
                                    }) {
                                        Text("Admin")
                                    }
                                }
                            }
                        },
                        onConfirm = { data ->
                            val name = data["name"] ?: ""
                            val phone = data["phone"]?.toIntOrNull() ?: 0
                            val password = data["password"] ?: ""
                            val mail = data["email"] ?: ""
                            val role = selectedRole
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(mail, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                                        userId?.let { uid ->
                                            val user = User(
                                                uid = uid,
                                                name = name,
                                                email = mail,
                                                phone = phone,
                                                birthday = birthDate?.let { firebaseDateFormat.format(it) } ?: "",
                                                password = password,
                                                privacyPolicyAccepted = true,
                                                role = role
                                            )
                                            authRepo.saveUserToDatabase(user)
                                            showAddUserDialog = false
                                        }
                                    } else {
                                        Log.e("UserManagementScreen", "Failed to create user in FirebaseAuth: ${task.exception?.message}")
                                    }
                                }
                        },
                        onDismiss = { showAddUserDialog = false }
                    )
                }


                if (showEditUserDialog) {
                    FormComponent(
                        title = "Chỉnh sửa người dùng",
                        fields = listOf(
                            FormField("name", "Name", selectedUser?.name ?: ""),
                            FormField("phone", "Phone", selectedUser?.phone?.toString() ?: ""),
                        ),
                        content = {
                            Column {
                                NovadiDatePickerDocked(
                                    labelValue = "Ngày sinh",
                                    painterResource = painterResource(id = R.drawable.date),
                                    selectedDate = birthDate,
                                    onDateChanged = { date ->
                                        birthDate = date
                                    }
                                )

                                var expanded by remember { mutableStateOf(false) }
                                Button(onClick = { expanded = true }) {
                                    Text(text = selectedRole)
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        selectedRole = "Customer"
                                        expanded = false
                                    }) {
                                        Text("Customer")
                                    }
                                    DropdownMenuItem(onClick = {
                                        selectedRole = "Admin"
                                        expanded = false
                                    }) {
                                        Text("Admin")
                                    }
                                }
                            }
                        },
                        onConfirm = { data ->
                            val name = data["name"] ?: ""
                            val phone = data["phone"]?.toIntOrNull() ?: 0
                            val password = selectedUser?.password
                            val mail = selectedUser?.email
                            val role = selectedRole

                            selectedUser?.let { user ->
                                val updatedUser = user.copy(
                                    name = name,
                                    phone = phone,
                                    birthday = birthDate?.let { firebaseDateFormat.format(it) } ?: "",
                                    password = password,
                                    email = mail,
                                    role = role
                                )
                                authRepo.updateUser(updatedUser)
                                showEditUserDialog = false
                            }
                        },
                        onDismiss = { showEditUserDialog = false }
                    )
                }

                BackButtonHandler {
                    BarBerShopAppRoute.navigateTo(Screen.AdminHomeScreen)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserManagementPreview() {
    MaterialTheme {
        Surface {
            UserManagementScreen()
        }
    }
}


