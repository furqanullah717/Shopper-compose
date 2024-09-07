package com.codewithfk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    val productId:Int,
    val userId:Int,
    val name: String,
    val price: Double,
    val imageUrl: String?,
    val quantity: Int,
    val productName: String
)