package com.codewithfk.shopper.ui.feature.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codewithfk.domain.model.OrdersData
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrdersScreen(viewModel: OrdersViewModel = koinViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Orders",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.titleMedium
            )
        }

        val uiState = viewModel.ordersEvent.collectAsState()
        //Tab Row
        val tabs = listOf("All", "Pending", "Delivered", "Cancelled")
        val selelctedTab = remember {
            mutableStateOf(0)
        }
        TabRow(selectedTabIndex = selelctedTab.value) {
            tabs.forEachIndexed { index, title ->
                Box(modifier = Modifier.clickable {
                    selelctedTab.value = index
                }) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        when (uiState.value) {
            is OrdersEvent.Loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Loading")
                }
            }

            is OrdersEvent.Success -> {
                val orders = (uiState.value as OrdersEvent.Success).data

                when (selelctedTab.value) {
                    0 -> {
                        OrderList(orders = orders)
                    }

                    1 -> {
                        OrderList(orders = viewModel.filterOrders(orders, "Pending"))
                    }

                    2 -> {
                        OrderList(orders = viewModel.filterOrders(orders, "Delivered"))
                    }

                    3 -> {
                        OrderList(orders = viewModel.filterOrders(orders, "Cancelled"))
                    }
                }
            }

            is OrdersEvent.Error -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = (uiState.value as OrdersEvent.Error).errorMsg)
                }
            }
        }
    }
}

@Composable
fun OrderList(orders: List<OrdersData>) {
    if (orders.isEmpty()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No Orders")
        }
    } else {
        LazyColumn {
            items(orders, key = { order -> order.id }) {
                OrderItem(order = it)
            }
        }
    }

}

@Composable
fun OrderItem(order: OrdersData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Color.LightGray.copy(alpha = 0.4f)
            )
            .padding(8.dp)
    ) {
        Text(text = "Order Id: ${order.id}")
        Text(text = "Order Date: ${order.orderDate}")
        Text(text = "Total Amount: ${order.totalAmount}")
        Text(text = "Status: ${order.status}")
    }
}