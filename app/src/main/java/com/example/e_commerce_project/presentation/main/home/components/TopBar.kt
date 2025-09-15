package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce_project.R
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.presentation.main.home.HomeIntent

@Composable
fun TopBar2(
    onIntent: (HomeIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(50.dp)
                .padding(start = 20.dp)
        ) {
            IconButton(onClick = { onIntent(HomeIntent.onLogoutClick) }) {
                Icon(
                    imageVector = Icons.Outlined.PowerSettingsNew,
                    contentDescription = "Log Out Icon",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(220.dp)
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside
            )
        }
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
//    TopBar(onIntent = { })
    TopBar()
}

@Composable
fun TopBar(
    onIntent: (HomeIntent) -> Unit = {},
    user: User = User("123", "paul", "smith.thompson@altostrat.com", "123")
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Hello ${user.name},", fontSize = 35.sp, fontWeight = FontWeight.W600)
        IconButton(onClick = { onIntent(HomeIntent.onLogoutClick) }) {
            Icon(
                imageVector = Icons.Outlined.PowerSettingsNew,
                contentDescription = "Log Out Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}