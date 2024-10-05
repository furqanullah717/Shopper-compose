package com.codewithfk.domain.repository

import com.codewithfk.domain.model.CartItemModel
import com.codewithfk.domain.model.CartModel
import com.codewithfk.domain.model.CartSummary
import com.codewithfk.domain.model.request.AddCartRequestModel
import com.codewithfk.domain.network.ResultWrapper

interface CartRepository {
    suspend fun addProductToCart(
        request: AddCartRequestModel
    ): ResultWrapper<CartModel>

    suspend fun getCart(): ResultWrapper<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel): ResultWrapper<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Int): ResultWrapper<CartModel>
    suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummary>
}