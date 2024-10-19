package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.CartRepository

class GetCartUseCase(val cartRepository: CartRepository) {
    suspend fun execute(userId: Long) = cartRepository.getCart(userId)
}