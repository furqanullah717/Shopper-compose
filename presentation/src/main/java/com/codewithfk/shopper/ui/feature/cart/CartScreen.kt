package com.codewithfk.shopper.ui.feature.cart

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.codewithfk.domain.model.CartItem
import com.codewithfk.shopper.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = koinViewModel(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val cartItems = viewModel.cartItems.collectAsState()
    val loading = remember {
        mutableStateOf(false)
    }
    val items = remember {
        mutableStateOf(emptyList<CartItem>())
    }
    val errorMsg = remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(cartItems.value) {
        when (cartItems.value) {
            is CartEvent.Loading -> {
                loading.value = true
                errorMsg.value = null
                showBottomSheet = false
            }

            is CartEvent.Error -> {
                loading.value = false
                errorMsg.value = (cartItems.value as CartEvent.Error).message
                showBottomSheet = true
            }

            is CartEvent.Success -> {
                val data = (cartItems.value as CartEvent.Success).cartItems
                items.value = data
                loading.value = false
                showBottomSheet = false
                errorMsg.value = null

            }

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val pullToRefreshState = rememberPullToRefreshState()
        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                viewModel.refresh()
                delay(1000)
                pullToRefreshState.endRefresh()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
            Column(
                modifier = Modifier
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                val shouldShowList = items.value.isNotEmpty()
                items.value.let {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        items(it, key = { it.id }) { cartItem ->
                            CartItemRow(modifier = Modifier.animateItem(),
                                cartItem = cartItem,
                                onRemoveClick = {
                                    viewModel.deleteItem(it)
                                },
                                onQuantityAdd = {
                                    if (cartItems.value is CartEvent.Loading) return@CartItemRow
                                    viewModel.increaseQuantity(it)
                                },
                                onQuantityRemove = {
                                    if (cartItems.value is CartEvent.Loading) return@CartItemRow
                                    viewModel.decreaseQuantity(it)
                                }

                            )
                        }

                    }
                }

                if (shouldShowList && cartItems.value !is CartEvent.Loading) {
                    Button(
                        onClick = { }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Proceed to Checkout")
                    }
                }
                AnimatedVisibility(
                    items.value.isEmpty() && cartItems.value !is CartEvent.Loading, enter = fadeIn()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "No Item in cart", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            AnimatedVisibility(loading.value, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.4f)
                        )
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                        Text(text = "Loading...")
                    }
                }
            }
        }
    }

    // Bottom sheet dialog for API errors
    if (showBottomSheet) {
        ModalBottomSheet(sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = {
                showBottomSheet = false
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Error",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(text = errorMsg.value ?: "An unexpected error occurred.")
                    Button(
                        onClick = {
                            showBottomSheet = false  // Dismiss the bottom sheet
                        }, modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Dismiss")
                    }
                }
            })
    }
}

@Composable
fun CartItemRow(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onRemoveClick: (CartItem) -> Unit,
    onQuantityAdd: (CartItem) -> Unit,
    onQuantityRemove: (CartItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.2f)),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        AsyncImage(
            model = cartItem.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(width = 126.dp, height = 96.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cartItem.productName,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${String.format("%.2f", cartItem.price)}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = { onRemoveClick(cartItem) }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Remove"
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            //quanitity with + and - circle button
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    onQuantityAdd(cartItem)

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = cartItem.quantity.toString())
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onQuantityRemove(cartItem)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_subtract),
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }
}

