package com.example.e_commerce_project.presentation.main.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_commerce_project.domain.model.Address
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.presentation.main.home.EmptyContent
import com.example.e_commerce_project.presentation.main.home.ErrorContent
import com.example.e_commerce_project.presentation.main.home.LoadingContent

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    onIntent: (ProfileIntent) -> Unit
) {
    when (uiState) {
        is ProfileUiState.Empty -> EmptyContent(message = uiState.message)
        is ProfileUiState.Error -> ErrorContent(message = uiState.message, onRetry = { })
        is ProfileUiState.Loading -> LoadingContent()
        is ProfileUiState.Success -> ProfileContent(
            modifier = modifier,
            user = uiState.user,
            addresses = uiState.addresses,
            isEditingProfile = uiState.isEditingProfile,
            isChangingPassword = uiState.isChangingPassword,
            isAddingAddress = uiState.isAddingAddress,
            onIntent = onIntent
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    user: User,
    addresses: List<Address>,
    isEditingProfile: Boolean,
    isChangingPassword: Boolean,
    isAddingAddress: Boolean,
    onIntent: (ProfileIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // User Info
        Text(text = "Name: ${user.name}")
        Text(text = "Email: ${user.email}")
        Text(text = "Phone: ${user.phone}")
        Spacer(Modifier.height(16.dp))

        // Profile Actions
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onIntent(ProfileIntent.ShowEditProfile) }) {
                Text("Edit Profile")
            }
            Button(onClick = { onIntent(ProfileIntent.ShowChangePassword) }) {
                Text("Change Password")
            }
        }

        Spacer(Modifier.height(24.dp))

        // Addresses Section
        Text(text = "Addresses", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        if (addresses.isEmpty()) {
            Text("No addresses saved.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(addresses) { address ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(address.title, style = MaterialTheme.typography.bodyLarge)
                                Text(address.address, style = MaterialTheme.typography.bodyMedium)
                            }
                            IconButton(
                                onClick = { onIntent(ProfileIntent.DeleteAddress(address.id)) }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete address"
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onIntent(ProfileIntent.ShowAddAddressDialog) }) {
                Text("Add Address")
            }
            Button(onClick = { onIntent(ProfileIntent.ClearAllAddresses) }) {
                Text("Clear All")
            }
        }
    }

    // Dialogs
    if (isEditingProfile) {
        EditProfileDialog(
            user = user,
            onConfirm = { name, phone, address ->
                onIntent(ProfileIntent.EditProfile(name, phone, address))
            },
            onDismiss = { onIntent(ProfileIntent.HideEditProfile) }
        )
    }

    if (isChangingPassword) {
        ChangePasswordDialog(
            onConfirm = { password ->
                onIntent(ProfileIntent.ChangePassword(password))
            },
            onDismiss = { onIntent(ProfileIntent.HideChangePassword) }
        )
    }

    if (isAddingAddress) {
        AddAddressDialog(
            onConfirm = { title, address ->
                onIntent(ProfileIntent.AddAddress(title, address))
            },
            onDismiss = { onIntent(ProfileIntent.HideAddAddressDialog) }
        )
    }
}

@Composable
fun EditProfileDialog(
    user: com.example.e_commerce_project.domain.model.User,
    onConfirm: (name: String, phone: String, address: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var phone by remember { mutableStateOf(user.phone) }
    var address by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Default Address") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name, phone, address) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ChangePasswordDialog(
    onConfirm: (password: String) -> Unit,
    onDismiss: () -> Unit
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (newPassword == confirmPassword && newPassword.isNotBlank()) {
                        onConfirm(newPassword)
                    }
                }
            ) {
                Text("Change")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddAddressDialog(
    onConfirm: (title: String, address: String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Address") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Address Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Full Address") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank() && address.isNotBlank()) {
                        onConfirm(title, address)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
