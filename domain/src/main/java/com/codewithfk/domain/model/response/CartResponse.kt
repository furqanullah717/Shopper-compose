package com.codewithfk.domain.model.response

import com.codewithfk.domain.model.CartItem
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val data: List<CartItem>,
    val msg: String
)