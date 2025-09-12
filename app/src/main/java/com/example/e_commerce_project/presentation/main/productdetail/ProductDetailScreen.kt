package com.example.e_commerce_project.presentation.main.productdetail

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
            storeName = uiState.storeName,
            isFavorite = uiState.isFavorite,
            onIntent = onIntent
        )
    }
}

@Composable
fun ProductDetailContent(
    modifier: Modifier = Modifier,
    product: Product,
    storeName: String,
    isFavorite: Boolean = false,
    onIntent: (ProductDetailIntent) -> Unit
) {
//    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 1)
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        // Jump instantly to the 2nd item (index = 1)
//        listState.animateScrollToItem(1)
        listState.animateScrollBy(
            value = 650f,
            animationSpec = tween(
                durationMillis = 1200, // make it slower (default ~300ms)
                easing = LinearOutSlowInEasing
            )
        )
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Image section with overlay favorites button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            LazyRow(
                state = listState
            ) {
                if (product.images.isNotEmpty()) {
                    items(product.images) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = product.title,
                            modifier = Modifier
                                .fillParentMaxWidth(0.7f)
                                .height(300.dp), // control height per image
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    item {
                        Image(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "No Image",
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(300.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
            // Favorites button overlay
            IconButton(
                onClick = {
                    if (!isFavorite) {
                        onIntent(
                            ProductDetailIntent.AddToFavorite(
                                store = storeName,
                                productId = product.id
                            )
                        )
                    } else {
                        onIntent(
                            ProductDetailIntent.DeleteFromFavorites(
                                productId = product.id
                            )
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        shape = CircleShape
                    )
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Scrollable content section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Title with responsive typography
            Text(
                text = product.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price and rating row - responsive layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price section
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    if (product.saleState) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$${product.price}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    textDecoration = TextDecoration.LineThrough
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$${product.salePrice}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    } else {
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Rating section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rate.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Stock info with better visual treatment
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inventory2,
                    contentDescription = "Stock",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${product.count} in stock",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (product.count > 0)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Description section
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 20.sp
                ),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons - responsive layout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Favorites button (compact)
                OutlinedButton(
                    onClick = {
                        if (!isFavorite) {
                            onIntent(
                                ProductDetailIntent.AddToFavorite(
                                    store = storeName,
                                    productId = product.id
                                )
                            )
                        } else {
                            onIntent(
                                ProductDetailIntent.DeleteFromFavorites(
                                    productId = product.id
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(0.3f)
                        .height(50.dp),
                    border = BorderStroke(
                        1.dp,
                        if (isFavorite) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Add to Cart button (primary)
                Button(
                    onClick = {
                        onIntent(
                            ProductDetailIntent.AddToCart(
                                store = storeName,
                                productId = product.id
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(0.7f)
                        .height(50.dp),
                    enabled = product.count > 0
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (product.count > 0) "Add to Cart" else "Out of Stock",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Bottom spacing for better scroll experience
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}