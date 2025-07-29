package com.example.e_commerce_project.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.e_commerce_project.R



@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onRegisterButtonClicked: () -> Unit = {},
    onLoginButtonClicked: () -> Unit = {}
) {

    Box(modifier = Modifier)
    {

        RemoteImageWithPlaceholder()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            )
        {

            Button(
                modifier = Modifier
                    .size(width = 250.dp, height = 60.dp),
                onClick = onLoginButtonClicked
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.size(15.dp))
            Button(
                modifier = Modifier.size(width = 250.dp, height = 60.dp),
                onClick = onRegisterButtonClicked
            ) {
                Text("Register")
            }
        }


    }
}

@Composable
fun RemoteImageWithPlaceholder() {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data("https://png.pngtree.com/png-clipart/20200406/ourmid/pngtree-business-concept-of-online-shopping-e-commerce-png-image_2174632.jpg")
        .crossfade(true)
        .placeholder(R.drawable.welcome_placeholder_image2)
        .error(R.drawable.welcome_placeholder_image2)
        .build()
    AsyncImage(
        model = imageRequest,
        contentDescription = "Random image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = 0.5f),
        contentScale = ContentScale.Fit
    )
}

