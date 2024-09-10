package com.codewithfk.domain.network

import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.response.CartResponse
import com.codewithfk.domain.model.response.CategoryResponse
import com.codewithfk.domain.model.response.ProductResponse

interface NetworkService {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductResponse>
    suspend fun getCategories(): ResultWrapper<CategoryResponse>
    suspend fun addProductToCart(product: Product, userId: Int): ResultWrapper<CartResponse>
    suspend fun getCart(userId: Int): ResultWrapper<CartResponse>
    suspend fun updateQuantity(cartItem: CartItem, userId: Int): ResultWrapper<CartResponse>
    suspend fun removeProductFromCart(cartItemId: Int, userId: Int): ResultWrapper<CartResponse>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}