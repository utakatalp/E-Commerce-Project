package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.HomeIntent

@Composable
fun ProductCard(
    onIntent: (HomeIntent) -> Unit,
    product: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(
                fraction = 0.4f
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onIntent(HomeIntent.onProductClick(product.id.toString())) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(8.dp)
        ) {
            // Product image (first one in the list)
            if (product.images.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(product.images[0]),
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = product.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Price
            Text(
                text = "${product.price} ₺",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}