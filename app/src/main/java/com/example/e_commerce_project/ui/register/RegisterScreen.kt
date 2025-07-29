package com.example.e_commerce_project.ui.register

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_project.R


@Preview
@Composable
fun RegisterScreen(
    onNavigateBackPressed: () -> Unit = { },
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.registerUiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { RegisterTopBar(navigateUp = onNavigateBackPressed) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Sign Up", fontSize = 40.sp, modifier = Modifier.padding(bottom = 80.dp))
            TextField(
                value = uiState.name,
                onValueChange = { viewModel.onIntent(RegisterIntent.EnterName(it)) },
                placeholder = { Text("Name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Name")
                }
                // label = { Text("Enter sth.")}
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.surname,
                onValueChange = { viewModel.onIntent(RegisterIntent.EnterSurname(it)) },
                placeholder = { Text("Surname") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Surname")
                }
                // label = { Text("Enter sth.")}
            )
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.email,
                onValueChange = { viewModel.onIntent(RegisterIntent.EnterEmail(it)) },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                }

            )
            if(uiState.warning != null) {
                uiState.warning!!.forEach { warning ->
                    Text(text = warning.errorMessage.orEmpty())
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            TextField(
                value = uiState.password,
                onValueChange = { viewModel.onIntent(RegisterIntent.EnterPassword(it)) },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                }
                // label = { Text("Enter sth.")}
            )
            Spacer(modifier = Modifier.size(25.dp))
            Button(
                onClick = {
                    viewModel.onIntent(RegisterIntent.SubmitRegister)
                          },
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