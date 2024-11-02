package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.CartItemModel
import com.codewithfk.domain.repository.CartRepository

class UpdateQuantityUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(cartItemModel: CartItemModel, userId: Long) =
        cartRepository.updateQuantity(cartItemModel, userId)
}