package com.codewithfk.shopper.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.usecase.GetCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(val userCase: GetCartUseCase) : ViewModel() {
    val _cartItems: MutableStateFlow<CartEvent> = MutableStateFlow(CartEvent.Loading)
    val cartItems = _cartItems.asStateFlow()

    init {
        getCartItems()

    }

    fun refresh() {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch {
            _cartItems.value = CartEvent.Loading
            when (val result = userCase.execute(1)) {
                is com.codewithfk.domain.network.ResultWrapper.Success -> {
                    val cartItems = result.value.data
                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    _cartItems.value = CartEvent.Success(cartItems, totalPrice)
                }

                is com.codewithfk.domain.network.ResultWrapper.Failure -> {
                    _cartItems.value = CartEvent.Error("Something went wrong!")
                }
            }
        }
    }

}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Error(val message: String) : CartEvent()
    data class Success(val cartItems: List<CartItem>, val totalPrice: Double) : CartEvent()
}