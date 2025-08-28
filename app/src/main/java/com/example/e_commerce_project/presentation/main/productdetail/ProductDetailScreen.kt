package com.example.e_commerce_project.presentation.main.productdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerce_project.R
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.EmptyContent
import com.example.e_commerce_project.presentation.main.home.ErrorContent
import com.example.e_commerce_project.presentation.main.home.LoadingContent

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState,
    onIntent: (ProductDetailIntent) -> Unit
) {
    when (uiState) {
        is ProductDetailUiState.Empty -> EmptyContent(message = uiState.message)
        is ProductDetailUiState.Error -> ErrorContent(message = uiState.message, onRetry = { })
        is ProductDetailUiState.Loading -> LoadingContent()
        is ProductDetailUiState.Success -> ProductDetailContent(
            modifier = modifier,
            product = uiState.product,
            onIntent = onIntent
        )
    }
}

@Composable
fun ProductDetailContent(
    modifier: Modifier = Modifier,
    product: Product,
    onIntent: (ProductDetailIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Product image
        if (product.images.isNotEmpty()) {
            AsyncImage(
                model = product.images[0],
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "No Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Price section
        if (product.saleState) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$${product.price}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textDecoration = TextDecoration.LineThrough
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$${product.salePrice}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        } else {
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Rating and stock
        Text(
            text = "⭐ ${product.rate}  |  ${product.count} in stock",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = product.description,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Add to Cart button
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Add to Cart", style = MaterialTheme.typography.titleMedium)
        }
    }
}