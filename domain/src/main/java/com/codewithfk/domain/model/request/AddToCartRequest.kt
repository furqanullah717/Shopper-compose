package com.codewithfk.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int, // Link cart item to the user
)