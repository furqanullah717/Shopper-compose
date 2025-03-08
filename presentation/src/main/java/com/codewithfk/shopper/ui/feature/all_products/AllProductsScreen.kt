package com.codewithfk.shopper.ui.feature.all_products

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.codewithfk.domain.model.Product
import com.codewithfk.shopper.R
import com.codewithfk.shopper.model.UiProductModel
import com.codewithfk.shopper.navigation.ProductDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllProductsScreen(
    navController: NavController, viewModel: AllProductsViewModel = koinViewModel()
) {
    val uiState = viewModel.uiEvent.collectAsState()
    val state = viewModel.state.collectAsState()

    val uiLoading = remember {
        mutableStateOf(true)
    }
    val cartLoading = remember {
        mutableStateOf(true)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }
    val products = remember {
        mutableStateOf<List<Product>>(emptyList())

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "back",
                modifier = Modifier
                    .clickable{
                        navController.popBackStack()
                    }
                    .align(Alignment.TopStart)

            )
            Text(
                text = "Products",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )
        }

        LaunchedEffect(uiState.value) {
            when (uiState.value) {
                is AllProductsScreenUIEvents.Loading -> {
                    uiLoading.value = true
                    error.value = null
                }

                is AllProductsScreenUIEvents.Success -> {
                    val data = (uiState.value as AllProductsScreenUIEvents.Success)
                    products.value = data.products
                    uiLoading.value = false
                    error.value = null
                }

                is AllProductsScreenUIEvents.Error -> {
                    val errorMsg = (uiState.value as AllProductsScreenUIEvents.Error).message
                    uiLoading.value = false
                    error.value = errorMsg
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ){
            if(uiLoading.value){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            }
            else{
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(126.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(products.value,key = {it.id}){ product ->
                        ProductItem(
                            product,
                            onClick = {
                                navController.navigate(ProductDetails(UiProductModel.fromProduct(product)))
                            },
                            onAddToCart = {
                                viewModel.addProductToCart(UiProductModel.fromProduct(product))
                            },
                            isEnabled = !cartLoading.value
                        )
                    }
                }
                LaunchedEffect(state.value) {
                    when (state.value) {
                        is AllProductsScreenCartUIEvents.Error -> {
                            Toast.makeText(
                                navController.context,
                                (state.value as AllProductsScreenCartUIEvents.Error).message,
                                Toast.LENGTH_LONG
                            ).show()
                            cartLoading.value = false
                        }

                        is AllProductsScreenCartUIEvents.Loading -> {
                            cartLoading.value = true
                        }

                        is AllProductsScreenCartUIEvents.Success -> {
                            cartLoading.value = false
                            Toast.makeText(
                                navController.context,
                                (state.value as AllProductsScreenCartUIEvents.Success).message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> {
                            cartLoading.value = false
                        }
                    }
                }
                if (cartLoading.value) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(0.5f)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Adding to cart...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick:()->Unit, onAddToCart: () -> Unit, isEnabled: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 126.dp, height = 144.dp)
            .clickable(enabled = isEnabled) {
                onClick()
            },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.image,
                error = painterResource(R.drawable.shoppers),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(Color.White),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Image(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(enabled = isEnabled){
                            onAddToCart()
                        }

                )
            }
        }
    }
}