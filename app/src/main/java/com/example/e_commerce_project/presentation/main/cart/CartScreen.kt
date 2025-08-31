package com.example.e_commerce_project.presentation.main.cart


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerce_project.R
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.EmptyContent
import com.example.e_commerce_project.presentation.main.home.ErrorContent
import com.example.e_commerce_project.presentation.main.home.LoadingContent

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    uiState: CartUiState,
    onIntent: (CartIntent) -> Unit
) {
    when (uiState) {
        is CartUiState.Loading -> LoadingContent()
        is CartUiState.Error -> ErrorContent(
            message = uiState.message,
            onRetry = { onIntent(CartIntent.RefreshCart) }
        )

        is CartUiState.Empty -> EmptyContent(message = uiState.message)
        is CartUiState.Success -> CartContent(
            modifier = modifier,
            uiState = uiState,
            onIntent = onIntent
        )
    }
}

@Composable
fun CartContent(
    modifier: Modifier = Modifier,
    uiState: CartUiState.Success,
    onIntent: (CartIntent) -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cart (${uiState.cartProducts.size})")
                    }
                }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Favorites (${uiState.favoritesProducts.size})")
                    }
                }
            )
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> CartTabContent(
                products = uiState.cartProducts,
                onIntent = onIntent
            )

            1 -> FavoritesTabContent(
                products = uiState.favoritesProducts,
                onIntent = onIntent
            )
        }
    }
}

@Composable
fun CartTabContent(
    products: List<Product>,
    onIntent: (CartIntent) -> Unit
) {
    if (products.isEmpty()) {
        EmptyContent(message = "Your cart is empty")
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    CartItemCard(
                        product = product,
                        onProductClick = {
                            onIntent(CartIntent.onProductClick(product.id.toString(), "canerture"))
                        },
                        onDeleteClick = {
                            onIntent(CartIntent.deleteFromCart(product.id))
                        },
                        showDeleteButton = true
                    )
                }
            }

            // Cart Summary and Checkout
            CartSummary(
                products = products,
                onCheckout = { /* Handle checkout */ }
            )
        }
    }
}

@Composable
fun FavoritesTabContent(
    products: List<Product>,
    onIntent: (CartIntent) -> Unit
) {
    if (products.isEmpty()) {
        EmptyContent(message = "No favorites yet")
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                CartItemCard(
                    product = product,
                    onProductClick = {
                        onIntent(CartIntent.onProductClick(product.id.toString(), "canerture"))
                    },
                    onDeleteClick = {
                        onIntent(CartIntent.deleteFromFavorites(product.id))
                    },
                    showDeleteButton = true,
                    isFavoriteItem = true
                )
            }
        }
    }
}

@Composable
fun CartItemCard(
    product: Product,
    onProductClick: () -> Unit,
    onDeleteClick: () -> Unit,
    showDeleteButton: Boolean = true,
    isFavoriteItem: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                if (product.images.isNotEmpty()) {
                    AsyncImage(
                        model = product.images[0],
                        contentDescription = product.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "No Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

                // Favorite indicator for favorite items
                if (isFavoriteItem) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rate.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price
                if (product.saleState) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
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
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Delete Button
            if (showDeleteButton) {
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = if (isFavoriteItem) "Remove from favorites" else "Remove from cart",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CartSummary(
    products: List<Product>,
    onCheckout: () -> Unit
) {
    val totalPrice = products.sumOf {
        if (it.saleState) it.salePrice else it.price
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Items (${products.size})",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "$${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = products.isNotEmpty()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCartCheckout,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Proceed to Checkout",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}


