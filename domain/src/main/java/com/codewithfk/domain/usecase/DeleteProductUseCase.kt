package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.CartItemModel
import com.codewithfk.domain.repository.CartRepository

class DeleteProductUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(cartItemId: Int, userId: Long) = cartRepository.deleteItem(cartItemId, userId)
}