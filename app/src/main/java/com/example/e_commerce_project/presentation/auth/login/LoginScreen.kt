package com.example.e_commerce_project.presentation.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email

import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun LoginScreen(
    onIntent: (LoginIntent) -> Unit,
    uiState: LoginUiState,
) {

    Scaffold()
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Log In", fontSize = 40.sp, modifier = Modifier.padding(bottom = 40.dp))
            Box(modifier = Modifier.height(40.dp)) {
                this@Column.AnimatedVisibility(
                    visible = uiState.wrongPasswordOrEmail,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "Wrong password or email.",
                        fontSize = 15.sp
                    )
                }
            }

            TextField(
                value = uiState.email,
                onValueChange = { onIntent(LoginIntent.EnterEmail(it)) },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                }
            )
            Spacer(modifier = Modifier.size(20.dp))


            TextField(
                value = uiState.password,
                onValueChange = { onIntent(LoginIntent.EnterPassword(it)) },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (uiState.showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (uiState.showPassword) "Hide password" else "Show password",
                        modifier = Modifier.clickable { onIntent(LoginIntent.ShowPassword) }
                    )
                },
                visualTransformation = if (uiState.showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            )

            TextButton(
                onClick = { onIntent(LoginIntent.ClickForgotPassword) },
                Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = 40.dp),

                ) {
                Text("Did you forget your password?")
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = { onIntent(LoginIntent.SubmitLogin) },
                modifier = Modifier.size(width = 250.dp, height = 60.dp)
            ) {
                Text("Log In")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar(
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


