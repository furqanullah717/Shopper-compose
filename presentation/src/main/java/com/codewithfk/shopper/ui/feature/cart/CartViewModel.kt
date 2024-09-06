package com.codewithfk.shopper.ui.feature.cart

import androidx.lifecycle.ViewModel
import com.codewithfk.domain.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {
    val _state:MutableStateFlow<CartEvent> = MutableStateFlow(CartEvent.Loading)
    val state = _state.asStateFlow()

}

sealed class CartEvent {
    data object Loading : CartEvent()
    data class Error(val message: String) : CartEvent()
    data class Success(val cartItems: List<CartItem>, val totalPrice: Double) : CartEvent()
}