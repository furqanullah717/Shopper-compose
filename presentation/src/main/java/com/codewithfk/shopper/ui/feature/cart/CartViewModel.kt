package com.codewithfk.shopper.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.model.response.CartResponse
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.usecase.DeleteCartProductUseCase
import com.codewithfk.domain.usecase.GetCartUseCase
import com.codewithfk.domain.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    val userCase: GetCartUseCase,
    val updateQuantityUseCase: UpdateQuantityUseCase,
    val delteProductUseCase: DeleteCartProductUseCase
) : ViewModel() {
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
                is ResultWrapper.Success -> {
                    val cartItems = result.value.data
                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    _cartItems.value = CartEvent.Success(cartItems, totalPrice)
                }

                is ResultWrapper.Failure -> {
                    _cartItems.value = CartEvent.Error("Something went wrong!")
                }
            }
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        processQuantity(cartItem.copy(quantity = cartItem.quantity.inc()))
    }

    private fun processQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = CartEvent.Loading
            when (val result = updateQuantityUseCase.execute(
                cartItem,
                cartItem.userId
            )) {
                is ResultWrapper.Success<*> -> {
                    val cartItems = (result.value as CartResponse).data
                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    _cartItems.value = CartEvent.Success(cartItems, totalPrice)
                }

                is ResultWrapper.Failure -> _cartItems.value =
                    CartEvent.Error("Failed to increase quantity.")
            }
        }
    }

    // Handling Decrease Quantity with error feedback
    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch {
            if (cartItem.quantity == 1) {
                _cartItems.value = CartEvent.Error("Make sure item is greater than 1.")
                return@launch
            }
            processQuantity(cartItem.copy(quantity = cartItem.quantity.dec()))
        }
    }

    fun deleteItem(cartItem: CartItem) {
        viewModelScope.launch {
            _cartItems.value = CartEvent.Loading
            when (val result = delteProductUseCase.execute(cartItem.id, cartItem.userId)) {
                is ResultWrapper.Success<*> -> {
                    val cartItems = (result.value as CartResponse).data
                    val totalPrice = cartItems.sumOf { it.price * it.quantity }
                    _cartItems.value = CartEvent.Success(cartItems, totalPrice)
                }
                is ResultWrapper.Failure -> _cartItems.value = CartEvent.Error("Failed to delete item.")
            }
        }
    }
}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Error(val message: String) : CartEvent()
    data class Success(val cartItems: List<CartItem>, val totalPrice: Double) : CartEvent()

}