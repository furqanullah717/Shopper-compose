package com.codewithfk.shopper.ui.feature.all_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.request.AddCartRequestModel
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.usecase.AddToCartUseCase
import com.codewithfk.domain.usecase.GetProductUseCase
import com.codewithfk.shopper.ShopperSession
import com.codewithfk.shopper.model.UiProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllProductsViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val shopperSession: ShopperSession
) : ViewModel(){

    private val _uiEvent = MutableStateFlow<AllProductsScreenUIEvents>(AllProductsScreenUIEvents.Loading)
    val uiState = _uiEvent.asStateFlow()

    private val _state = MutableStateFlow<AllProductsScreenCartUIEvents>(AllProductsScreenCartUIEvents.Nothing)
    val state = _state.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiEvent.value = AllProductsScreenUIEvents.Loading
            val products = getProducts()
            if (products.isEmpty()) {
                _uiEvent.value = AllProductsScreenUIEvents.Error("Failed to load products")
                return@launch
            }
            _uiEvent.value = AllProductsScreenUIEvents.Success(products)
        }
    }

    private suspend fun getProducts(): List<Product> {
        getProductUseCase.execute(null).let { result ->
            return when (result) {
                is ResultWrapper.Success -> {
                    (result).value.products
                }

                is ResultWrapper.Failure -> {
                    emptyList()
                }
            }
        }
    }

    fun addProductToCart(product: UiProductModel) {
        viewModelScope.launch {
            _state.value = AllProductsScreenCartUIEvents.Loading
            val userDomainModel = shopperSession.getUser()

            val result = addToCartUseCase.execute(
                AddCartRequestModel(
                    product.id,
                    product.title,
                    product.price,
                    1,
                    userDomainModel!!.id!!
                ),
                userDomainModel.id!!.toLong()
            )

            when (result) {
                is ResultWrapper.Success -> {
                    _state.value = AllProductsScreenCartUIEvents.Success("Product added to cart")
                }

                is ResultWrapper.Failure -> {
                    _state.value = AllProductsScreenCartUIEvents.Error("Something went wrong!")
                }
            }
        }
    }
}

//for events related to all loading of products
sealed class AllProductsScreenUIEvents {
    data object Loading : AllProductsScreenUIEvents()
    data class Success(
        val products: List<Product>,
    ) : AllProductsScreenUIEvents()
    data class Error(val message: String) : AllProductsScreenUIEvents()
}

// for events related to adding products to cart
sealed class AllProductsScreenCartUIEvents{
    data object Loading : AllProductsScreenCartUIEvents()
    data object Nothing : AllProductsScreenCartUIEvents()
    data class Error(val message: String) : AllProductsScreenCartUIEvents()
    data class Success(val message: String) : AllProductsScreenCartUIEvents()
}
