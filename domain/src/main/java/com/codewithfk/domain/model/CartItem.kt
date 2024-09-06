package com.codewithfk.domain.model

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val productName: String
)