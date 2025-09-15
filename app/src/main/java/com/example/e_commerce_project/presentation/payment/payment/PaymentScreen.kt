package com.example.e_commerce_project.presentation.payment.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.e_commerce_project.presentation.main.home.EmptyContent
import com.example.e_commerce_project.presentation.main.home.ErrorContent
import com.example.e_commerce_project.presentation.main.home.LoadingContent

@Composable
fun PaymentScreen(
    uiState: PaymentUiState,
    onIntent: (PaymentIntent) -> Unit
) {
    when (uiState) {
        is PaymentUiState.Empty -> EmptyContent(message = uiState.message)
        is PaymentUiState.Error -> ErrorContent(message = uiState.message, onRetry = { })
        is PaymentUiState.Loading -> LoadingContent()
        is PaymentUiState.Success -> PaymentContent(
            uiState = uiState,
            onIntent = onIntent
        )
    }
}

@Composable
fun PaymentContent(
    uiState: PaymentUiState.Success,
    onIntent: (PaymentIntent) -> Unit
) {
    Column {
        Text(text = "Select Address")
        uiState.addresses.forEachIndexed { i, item ->
            Row {
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = { onIntent(PaymentIntent.SelectAddress(i)) }
                )
                Text(item.address.address)
            }

        }
    }

}