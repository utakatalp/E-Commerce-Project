package com.example.e_commerce_project.presentation.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.components.LogOutCard
import com.example.e_commerce_project.presentation.main.home.components.ProductCard


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
    Column {
        LogOutCard { onIntent(HomeIntent.onLogoutClick) }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            if (uiState.stores.isNotEmpty()) {
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