package com.example.e_commerce_project.presentation.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_project.R



@Composable
fun RegisterScreen(
    onIntent: (RegisterIntent) -> Unit,
    uiState: RegisterUiState
) {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(!uiState.warning.isEmpty()) {
                uiState.warning.forEach { warning ->
                    Text(text = warning.errorMessage.orEmpty())
                }
            }
            Text(text = "Sign Up", fontSize = 40.sp, modifier = Modifier.padding(bottom = 80.dp))
            TextField(
                value = uiState.name,
                onValueChange = { onIntent(RegisterIntent.EnterName(it)) },
                placeholder = { Text("Name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Name")
                }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.email,
                onValueChange = { onIntent(RegisterIntent.EnterEmail(it)) },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.phone,
                onValueChange = { onIntent(RegisterIntent.EnterPhone(it)) },
                placeholder = { Text("Phone") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Email")
                }
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.password,
                onValueChange = { onIntent(RegisterIntent.EnterPassword(it)) },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (uiState.showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (uiState.showPassword) "Hide password" else "Show password",
                        modifier = Modifier.clickable { onIntent(RegisterIntent.ShowPassword) }
                    )
                },
                visualTransformation = if (uiState.showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

                // label = { Text("Enter sth.")}
            )
            Spacer(modifier = Modifier.size(25.dp))
            Button(
                onClick = { onIntent(RegisterIntent.SubmitRegister) },
                modifier = Modifier.size(width = 250.dp, height = 60.dp)
            ) {
                Text("Register")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTopBar(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}