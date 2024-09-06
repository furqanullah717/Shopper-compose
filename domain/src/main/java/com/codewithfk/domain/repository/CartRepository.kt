package com.codewithfk.domain.repository

import com.codewithfk.domain.model.CartItem

interface CartRepository {

    fun getCartItems(): List<CartItem>
}