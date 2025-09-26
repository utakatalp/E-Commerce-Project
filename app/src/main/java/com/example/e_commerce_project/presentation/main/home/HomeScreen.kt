package com.example.e_commerce_project.presentation.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.e_commerce_project.presentation.main.home.components.CategoryCard
import com.example.e_commerce_project.presentation.main.home.components.CustomizableSearchBar
import com.example.e_commerce_project.presentation.main.home.components.ProductCard
import com.example.e_commerce_project.presentation.main.home.components.TopBar


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onIntent: (HomeIntent) -> Unit
) {
    when (uiState) {
        is HomeUiState.Empty -> EmptyContent(message = uiState.message)
        is HomeUiState.Error -> ErrorContent(message = uiState.message, onRetry = { })
        is HomeUiState.Loading -> LoadingContent()
        is HomeUiState.Success -> HomeContent(
            uiState = uiState,
            onIntent = onIntent,
        )
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
fun HomeContent(
    uiState: HomeUiState.Success,
    onIntent: (HomeIntent) -> Unit
) {
    val gridState = rememberLazyGridState()
    val categoryState = rememberLazyListState()
    Column {
        TopBar(
            onIntent = onIntent,
            user = uiState.user,
        )
        CustomizableSearchBar(
            onIntent = onIntent,
            query = uiState.searchText,
            onQueryChange = { onIntent(HomeIntent.Search(it)) },
            onSearch = { onIntent(HomeIntent.Search(it)) },
            onExpandedChange = { onIntent(HomeIntent.ExpandSearch(it)) },
            searchResults = uiState.filteredList,
            uiState = uiState,
        )
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2)
        ) {
            stickyHeader {
                Column {
                    if (gridState.firstVisibleItemIndex > 2) {
                        LazyRow(
                            state = categoryState,
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.LightGray.copy(alpha = 0.9f),
                                        Color.Transparent
                                    )
                                )
                            )
                        ) {
                            itemsIndexed(uiState.allCategories.toList()) { _, item ->
                                CategoryCard(category = item, onIntent = onIntent)
                            }
                        }
                    }
                }
            }

            uiState.stores.forEach { store ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            "${store.name}'s store: ",
                            modifier = Modifier.padding(8.dp)
                        )
                        LazyRow {
                            items(store.products) { product ->
                                ProductCard(
                                    product = product,
                                    onIntent = onIntent,
                                    isFavorite = product.isFavorite
                                )
                            }
                        }
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                val visible = gridState.firstVisibleItemIndex <= 2
                LazyRow(
                    state = categoryState,
                    contentPadding = PaddingValues(
                        10.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.alpha(if (visible) 1f else 0f)
                ) {
                    itemsIndexed(uiState.allCategories.toList()) { index, item ->
                        CategoryCard(
                            category = item,
                            onIntent = onIntent
                        )
                    }
                }
            }

            items(uiState.products.size) { index ->
                val product = uiState.products[index]
                ProductCard(
                    onIntent = onIntent,
                    product = product,
                    isFavorite = product.isFavorite
                )
            }
        }
    }
}
@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit = { },

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