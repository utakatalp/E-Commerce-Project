package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.HomeIntent

@Composable
fun SearchedProductCard(
    onIntent: (HomeIntent) -> Unit,
    product: Product,
    isFavorite: Boolean = false,
) {
    Card(
        onClick = { onIntent(HomeIntent.onProductClick(product.id.toString(), product.storeName)) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.images[0]),
                contentDescription = product.title,
                modifier = Modifier.width(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(10.dp))
            Text(
                product.title,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        shape = CircleShape
                    )
                    .size(32.dp),
                onClick = {
                    if(isFavorite){
                        onIntent(HomeIntent.DeleteFromFavorites(product.id))
                    } else {
                        onIntent(HomeIntent.onFavoriteClick(product.id, product.storeName))
                    }

                }
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add to favorite",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
    HorizontalDivider(thickness = 2.dp)
}