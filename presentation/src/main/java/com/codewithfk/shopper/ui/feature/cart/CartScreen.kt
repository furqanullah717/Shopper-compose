package com.codewithfk.shopper.ui.feature.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.codewithfk.data.model.response.CartItem
import com.codewithfk.domain.model.CartItemModel
import com.codewithfk.shopper.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState()
    val cartItems = remember {
        mutableStateOf(emptyList<CartItemModel>())
    }
    val loading = remember {
        mutableStateOf(false)
    }
    val errorMsg = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(uiState.value) {
        when (uiState.value) {
            is CartEvent.Loading -> {
                loading.value = true
                errorMsg.value = null
            }

            is CartEvent.Error -> {
                // Show error
                loading.value = false
                errorMsg.value = (uiState.value as CartEvent.Error).message
            }

            is CartEvent.Success -> {
                loading.value = false
                val data = (uiState.value as CartEvent.Success).message
                if (data.isEmpty()) {
                    errorMsg.value = "No items in cart"
                } else {
                    cartItems.value = data
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        val pullToRefreshState = rememberPullToRefreshState()
        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                viewModel.getCart()
                delay(500)
                pullToRefreshState.endRefresh()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            PullToRefreshContainer(
                state = pullToRefreshState, modifier = Modifier.align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .padding(16.dp)
            ) {
                Text(text = "Cart", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.size(8.dp))
                val shouldShowList = !loading.value && errorMsg.value == null
                AnimatedVisibility(
                    visible = shouldShowList, enter = fadeIn(), modifier = Modifier.weight(1f)
                ) {
                    LazyColumn {
                        items(cartItems.value) { item ->
                            CartItem(item = item)
                        }
                    }
                }
                if(shouldShowList) {
                    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Checkout")
                    }
                }
            }
            if (loading.value) {
                // Show loading
                Column(modifier = Modifier.align(Alignment.Center)) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    Text(text = "Loading...")
                }
            }
            if (errorMsg.value != null) {
                Text(
                    text = errorMsg.value ?: "Something went wrong!",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }

}

@Composable
fun CartItem(item: CartItemModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.4f))
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(126.dp, 96.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.productName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {

            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete), contentDescription = null
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add), contentDescription = null
                    )
                }
                Text(text = item.quantity.toString())
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_subtract),
                        contentDescription = null
                    )
                }

            }
        }
    }

}