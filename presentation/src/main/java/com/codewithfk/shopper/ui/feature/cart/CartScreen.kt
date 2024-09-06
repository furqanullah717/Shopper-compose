package com.codewithfk.shopper.ui.feature.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codewithfk.domain.model.CartItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel(),
    onCheckoutClick: () -> Unit,
    onItemRemoved: (CartItem) -> Unit
) {
//    val cartItems = viewModel.cartItems.collectAsState().value
//    val totalPrice = viewModel.totalPrice.collectAsState().value

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Cart",
//            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        LazyColumn(
//            modifier = Modifier.weight(1f),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(cartItems.size) { index ->
//                CartItemRow(
//                    cartItem = cartItems[index],
//                    onRemoveClick = { onItemRemoved(cartItems[index]) }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Total: $${String.format("%.2f", totalPrice)}",
//            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = onCheckoutClick,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "Proceed to Checkout")
//        }
//    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemoveClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = cartItem.productName)
            Text(text = "Quantity: ${cartItem.quantity}")
            Text(text = "$${String.format("%.2f", cartItem.price)}")
        }
        IconButton(onClick = onRemoveClick) {
            Icon(Icons.Default.Delete, contentDescription = "Remove item")
        }
    }
}

