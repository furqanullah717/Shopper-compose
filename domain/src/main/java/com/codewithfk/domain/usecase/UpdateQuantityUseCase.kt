package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.repository.CartRepository

class UpdateQuantityUseCase(private val repository: CartRepository) {
    suspend fun execute(cartItem: CartItem, userId: Int) = repository.updateQuantity(cartItem, userId)
}