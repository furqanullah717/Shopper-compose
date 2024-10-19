package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.request.AddCartRequestModel
import com.codewithfk.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute(request: AddCartRequestModel, userId: Long) = cartRepository.addProductToCart(request,userId)
}