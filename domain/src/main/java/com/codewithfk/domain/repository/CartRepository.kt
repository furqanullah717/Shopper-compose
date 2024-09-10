package com.codewithfk.domain.repository

import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.response.CartResponse
import com.codewithfk.domain.network.ResultWrapper

interface CartRepository {
    suspend fun getCartItems(userId: Int): ResultWrapper<CartResponse>
    suspend fun addProductToCart(product: Product, userId: Int): ResultWrapper<CartResponse>
    suspend fun removeProductFromCart(cartItemId: Int, userId: Int): ResultWrapper<CartResponse>
    suspend fun updateQuantity(product: CartItem, userId: Int): ResultWrapper<CartResponse>
}