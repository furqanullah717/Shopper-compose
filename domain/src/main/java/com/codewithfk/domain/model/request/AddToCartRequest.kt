package com.codewithfk.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val id: Int? =null,
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int, // Link cart item to the user
)