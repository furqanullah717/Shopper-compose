package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.Product
import com.codewithfk.domain.repository.CartRepository

class GetCartUseCase(private val repository: CartRepository) {
    suspend fun execute(userId:Int) = repository.getCartItems(userId)
}