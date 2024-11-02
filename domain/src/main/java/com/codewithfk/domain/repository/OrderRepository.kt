package com.codewithfk.domain.repository

import com.codewithfk.domain.model.AddressDomainModel
import com.codewithfk.domain.model.OrdersListModel
import com.codewithfk.domain.network.ResultWrapper

interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel, userId: Long): ResultWrapper<Long>
    suspend fun getOrderList(userId: Long): ResultWrapper<OrdersListModel>
}