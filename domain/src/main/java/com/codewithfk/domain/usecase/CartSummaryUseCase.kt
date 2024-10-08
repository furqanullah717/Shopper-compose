package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.CartRepository

class CartSummaryUseCase (private val repository: CartRepository) {
    suspend fun execute(userId: Int) = repository.getCartSummary(userId)
}