package com.example.e_commerce_project.presentation.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.e_commerce_project.presentation.main.home.components.CategoryCard
import com.example.e_commerce_project.presentation.main.home.components.ProductCard
import com.example.e_commerce_project.presentation.main.home.components.TopBar


// Dummy data classes


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit
) {
    when(uiState) {
        is HomeUiState.Empty -> EmptyContent(message = uiState.message)
        is HomeUiState.Error -> ErrorContent(message = uiState.message, onRetry = { })
        is HomeUiState.Loading -> LoadingContent()
        is HomeUiState.Success -> HomeContent(
            uiState = uiState,
            onIntent = onIntent,
            modifier = modifier
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier,
    uiState: HomeUiState.Success,
    onIntent: (HomeIntent) -> Unit
) {
    Column(modifier = modifier) {
        TopBar(
            onIntent = onIntent,
            user = uiState.user,
        )
        LazyRow {
            items(uiState.stores[0].categories.size) { index ->
                val category = uiState.stores[0].categories[index]
                CategoryCard(category = category)
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            if (uiState.stores.isNotEmpty()) {
                uiState.stores.forEach { store ->
                    items(store.products.size) { index ->
                        val product = store.products[index]
                        ProductCard(
                            onIntent = onIntent,
                            product = product,
                            storeName = store.name,
                            isFavorite = product.isFavorite
                        )
                    }
                }
            }
        }
    }
    /*
    LazyColumn(modifier = modifier) {
        item { LogOutCard { onIntent(HomeIntent.onLogoutClick) } }
        if(uiState.stores.isNotEmpty()) {
            uiState.stores.forEach { store ->
                items(store.products.size) { index ->
                    val product = store.products[index]
                    ProductCard(
                        onIntent = onIntent,
                        product = product
                    )
                }

            }
        }
    }

     */
}
@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}@Composable
fun EmptyContent(
    message: String,
    onRetry: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message)
            Button(onClick = onRetry) { Text("Try again") }
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Oops, a problem occurred.\n$message",
                fontWeight = FontWeight.SemiBold
            )
            Button(onClick = onRetry) { Text("Retry") }
        }
    }
}