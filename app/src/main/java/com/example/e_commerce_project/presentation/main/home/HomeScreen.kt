package com.example.e_commerce_project.presentation.main.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.example.e_commerce_project.presentation.main.home.components.SearchedProductCard
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
        CustomizableSearchBar(
            onIntent = onIntent,
            query = uiState.searchText,
            onQueryChange = { onIntent(HomeIntent.Search(it)) },
            onSearch = { onIntent(HomeIntent.Search(it)) },
            onExpandedChange = { onIntent(HomeIntent.ExpandSearch(it)) },
            searchResults = uiState.filteredList,
            uiState = uiState
        )
        LazyRow(
            contentPadding = PaddingValues(
                10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(uiState.allCategories.toList()) { index, item ->
                CategoryCard(
                    category = item,
                    onIntent = onIntent
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
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
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = if(uiState.expanded){
                    0.dp
                } else 10.dp), // horizontal center
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

            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                items(searchResults.size) { index ->
                    val result = searchResults[index]
//                    ProductCard(
//                        onIntent = onIntent,
//                        product = result
//                    )
                    SearchedProductCard(
                        onIntent = onIntent,
                        product = result,
                        isFavorite = result.isFavorite
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    // Dummy data
    val sampleUser = User(
        id = "1",
        name = "John Doe",
        email = "john@example.com",
        phone = ""
    )

    val sampleCategories = setOf(
        Category(name = "Electronics", isSelected = false, image = ""),
        Category(name = "Clothing", isSelected = true, image = ""),
        Category(name = "Books", isSelected = false, image = "")
    )

    val sampleProducts: List<Product> = emptyList()

    val sampleStores = listOf(
        Store(
            name = "Tech Store",
            products = sampleProducts,
            categories = sampleCategories.toList()
        )
    )

    val sampleState = HomeUiState.Success(
        user = sampleUser,
        stores = sampleStores,
        filteredList = sampleProducts,
        allCategories = sampleCategories,
        clickedCategory = emptyList(),
        products = sampleProducts,
        searchText = "",
        expanded = false
    )

    HomeContent(
        modifier = Modifier,
        uiState = sampleState,
        onIntent = {}
    )
}


