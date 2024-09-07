package com.codewithfk.data.repository

import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.response.CartResponse
import com.codewithfk.domain.model.response.CategoryResponse
import com.codewithfk.domain.network.NetworkService
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.repository.CartRepository
import com.codewithfk.domain.repository.CategoryRepository

class CartRepositoryImpl(private val networkService: NetworkService) : CartRepository {
    override suspend fun getCartItems(userId: Int): ResultWrapper<CartResponse> {
        return networkService.getCart(userId)
    }

    override suspend fun addProductToCart(
        product: Product,
        userId: Int
    ): ResultWrapper<CartResponse> {
        return networkService.addProductToCart(product, userId)
    }
}