package com.example.e_commerce_project.presentation.main.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
            .size(width = 150.dp, height = 40.dp)
            .drawBehind {
                val strokePx = strokeWidth.toPx()
                val topBorderHeight = size.height / 2
                val bottomBorderHeight = size.height / 2

                drawRect(
                    color = Color.Transparent,
                    topLeft = Offset.Zero,
                    size = Size(width = size.width, height = topBorderHeight),
                    style = Stroke(width = strokePx)
                )

                drawRect(
                    color = Color.Green,
                    topLeft = Offset(x = 0f, y = topBorderHeight),
                    size = Size(width = size.width, height = bottomBorderHeight),
                    style = Stroke(width = strokePx)
                )
            }
            .clickable {
                onIntent(HomeIntent.CategoryClick(category))
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(strokeWidth), // Use the Dp value directly here
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Cyan)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(category.name)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CategoryCardPreview() {
//    // You must call the Composable function you want to preview inside here
//    CategoryCard(category = Category("Sample Category", ""))
//}