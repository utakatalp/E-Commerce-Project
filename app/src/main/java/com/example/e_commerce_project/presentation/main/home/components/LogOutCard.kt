package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_project.presentation.main.home.HomeIntent

@Composable
fun LogOutCard(modifier: Modifier = Modifier, onIntent: (HomeIntent) -> Unit){
    Card(
        modifier = modifier
            .height(50.dp)
            .clickable(
                onClick = { onIntent(HomeIntent.onLogoutClick) }
            ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
            Text(
                "Log Out",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}
@Preview
@Composable
fun LogOutCardPreview() {
    // Eğer bir tema kullanıyorsan buraya Theme sarmalı ekle (ör: MyAppTheme {})
    LogOutCard(
        modifier = Modifier,
        onIntent = {}
    )
}