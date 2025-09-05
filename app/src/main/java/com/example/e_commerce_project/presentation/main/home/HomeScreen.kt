package com.example.e_commerce_project.presentation.main.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_project.domain.model.Category
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.presentation.main.home.components.CategoryCard
import com.example.e_commerce_project.presentation.main.home.components.ProductCard
import com.example.e_commerce_project.presentation.main.home.components.TopBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
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
            itemsIndexed(uiState.allCategories.toList()) { index, item ->
                CategoryCard(
                    category = item,
                    onIntent = onIntent
                )
            }
        }
        CustomizableSearchBar(
            onIntent = onIntent,
            query = uiState.searchText,
            onQueryChange = { onIntent(HomeIntent.Search(it)) },
            onSearch = { onIntent(HomeIntent.Search(it)) },
            onExpandedChange = { onIntent(HomeIntent.ExpandSearch(it)) },
            searchResults = uiState.filteredList,
            uiState = uiState
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            if (!(uiState.isCategorySelected) && uiState.stores.isNotEmpty()) {
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
            } else {
                uiState.stores.forEach { store ->
                    store.products.forEach { product ->
                        item(product.id) {
                            ProductCard(
                                onIntent = onIntent,
                                product = product,
                                storeName = store.name,
                                isFavorite = product.isFavorite
                            )
                        }
                    }
                }
//                items(uiState.products.size) { index ->
//                    val product = uiState.products[index]
//                    ProductCard(
//                        onIntent = onIntent,
//                        product = product,
//                        storeName = "non-necessary"
//                    )
//
//                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomizableSearchBar(
    onIntent: (HomeIntent) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    searchResults: List<Product>,
    uiState: HomeUiState.Success
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Search bar stays at the top
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally), // horizontal center
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {
                        onSearch(query)
                        onExpandedChange(false)
                    },
                    placeholder = { Text("Search") },
                    expanded = uiState.expanded,
                    onExpandedChange = { onExpandedChange(it) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable {
                                if (query.isBlank()) {
                                    onExpandedChange(false)
                                } else {
                                    onQueryChange("")
                                }
                            }
                        )
                    }
                )
            },
            windowInsets = WindowInsets(top = 0.dp),
            expanded = uiState.expanded,
            onExpandedChange = { onExpandedChange(uiState.expanded) }
        ) {
            BackHandler(enabled = uiState.expanded) {
                Log.d("BackHandler", "Back pressed")
                onExpandedChange(false)
            }

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(searchResults.size) { index ->
                    val result = searchResults[index]
                    ProductCard(
                        onIntent = onIntent,
                        product = result,
                        storeName = uiState.stores[0].name
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomizableSearchBarPreview() {

    val fakeUser = User(
        id = "1",
        email = "test@example.com",
        name = "Test User",
        phone = "+90 555 555 55 55"
    )

    val fakeCategories = listOf(
        Category(
            name = "Notebook",
            image = "https://via.placeholder.com/150"
        ),
        Category(
            name = "Monitor",
            image = "https://via.placeholder.com/150"
        )
    )

    val fakeProducts = listOf(
        Product(
            id = 1,
            title = "Lenovo V17 G2 ITL",
            price = 9899.0,
            salePrice = 0.0,
            description = "Entry-level Lenovo laptop",
            category = "Notebook",
            images = listOf("https://via.placeholder.com/300"),
            rate = 4.2,
            count = 10,
            saleState = true,
            isFavorite = false
        ),
        Product(
            id = 2,
            title = "MSI STEALTH 17M",
            price = 40466.0,
            salePrice = 38999.0,
            description = "Gaming laptop with RTX GPU",
            category = "Notebook",
            images = listOf("https://via.placeholder.com/300"),
            rate = 4.7,
            count = 5,
            saleState = true,
            isFavorite = true
        )
    )

    val fakeStores = listOf(
        Store(
            name = "Fake Store",
            products = fakeProducts,
            categories = fakeCategories
        )
    )

    val fakeUiState = HomeUiState.Success(
        user = fakeUser,
        stores = fakeStores,
        filteredList = fakeProducts,
        searchText = "",
        expanded = false,
    )

    CustomizableSearchBar(
        onIntent = {},
        query = "",
        onQueryChange = {},
        onSearch = {},
        onExpandedChange = {},
        searchResults = fakeProducts,
        uiState = fakeUiState
    )
}