package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_commerce_project.domain.model.Category
import com.example.e_commerce_project.presentation.main.home.HomeIntent

@Composable
fun CategoryCard(
    category: Category,
    onIntent: (HomeIntent) -> Unit
) {
    val strokeWidth = 1.dp
    Box(
        modifier = Modifier
            .height(height = 40.dp)

    ) {
        Card(
            onClick = {
                onIntent(HomeIntent.CategoryClick(category))
            },
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (category.isSelected) Color.Red else Color.Cyan
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp), // Use the Dp value directly here ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(category.name)
                Spacer(Modifier.width(25.dp))
                Icon(
                    imageVector = if (category.isSelected) {
                        Icons.Filled.Clear
                    } else {
                        Icons.Filled.Add
                    },
                    contentDescription = category.name,
                    modifier = Modifier.size(20.dp)
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    // Sahte Category nesnesi
    val sampleCategory = Category(
        name = "Electronics",
        isSelected = false,
        image = ""
    )

    CategoryCard(
        category = sampleCategory,
        onIntent = {}
    )
}