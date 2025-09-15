package com.example.e_commerce_project.presentation.main.home.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.presentation.main.home.HomeIntent
import com.example.e_commerce_project.presentation.main.home.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableSearchBar(
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
                .padding(
                    horizontal = if (uiState.expanded) {
                        0.dp
                    } else 10.dp,
                ), // horizontal center
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