package com.codewithfk.domain.usecase

import com.codewithfk.domain.model.AddressDomainModel
import com.codewithfk.domain.repository.OrderRepository

class PlaceOrderUseCase(val orderRepository: OrderRepository) {
    suspend fun execute(addressDomainModel: AddressDomainModel, userId: Long) =
        orderRepository.placeOrder(addressDomainModel,userId)
}