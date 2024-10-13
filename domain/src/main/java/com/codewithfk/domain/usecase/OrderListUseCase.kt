package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.OrdersListModel
import com.codewithfk.domain.repository.OrderRepository

class OrderListUseCase(
    private val repository: OrderRepository
) {
    suspend fun execute() = repository.getOrderList()
}