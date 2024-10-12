package com.codewithfk.data.repository

import com.codewithfk.domain.model.AddressDomainModel
import com.codewithfk.domain.model.OrdersListModel
import com.codewithfk.domain.network.NetworkService
import com.codewithfk.domain.network.ResultWrapper
import com.codewithfk.domain.repository.OrderRepository

class OrderRepositoryImpl(private val networkService: NetworkService) : OrderRepository {
    override suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long> {
        return networkService.placeOrder(addressDomainModel, 1)
    }

    override suspend fun getOrderList(): ResultWrapper<OrdersListModel> {
        return networkService.getOrderList()
    }
}