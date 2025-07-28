package com.example.e_commerce_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.nio.file.WatchEvent


@Preview
@Composable
fun ForgotPasswordScreen(
    onBackToLoginButtonClicked: () -> Unit = { }
) {

    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Don't worry!", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(40.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
            }
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            modifier = Modifier.size(width = 280.dp, height = 60.dp),
            onClick = { },
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Send Mail")
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onBackToLoginButtonClicked,
            modifier = Modifier
                .padding(start = 25.dp ,end = 40.dp, bottom = 40.dp),

            ) {
            Text("Back to login") // Biraz daha sağa kaydırılacak
        }
    }
}