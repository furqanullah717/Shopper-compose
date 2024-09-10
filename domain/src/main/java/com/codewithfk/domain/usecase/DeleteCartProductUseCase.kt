package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.repository.CartRepository

class DeleteCartProductUseCase(private val repository: CartRepository) {
    suspend fun execute(cartItemID: Int, userId: Int) = repository.removeProductFromCart(cartItemID, userId)
}