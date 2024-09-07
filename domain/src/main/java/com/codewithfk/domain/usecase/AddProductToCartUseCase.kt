package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.Product
import com.codewithfk.domain.repository.CartRepository

class AddProductToCartUseCase(private val repository: CartRepository) {
    suspend fun execute(product: Product,userId:Int) = repository.addProductToCart(product, userId)
}