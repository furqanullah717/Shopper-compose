package com.codewithfk.domain.model

data class OrdersListModel(
    val `data`: List<OrdersData>,
    val msg: String
)