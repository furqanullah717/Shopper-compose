package com.codewithfk.domain.model

data class OrdersData(
    val id: Int,
    val items: List<OrderProductItem>,
    val orderDate: String,
    val status: String,
    val totalAmount: Double,
    val userId: Int
)