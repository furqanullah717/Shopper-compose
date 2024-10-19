package com.codewithfk.shopper.ui.feature.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.OrdersData
import com.codewithfk.domain.usecase.OrderListUseCase
import com.codewithfk.shopper.ShopperSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val orderListUseCase: OrderListUseCase
) : ViewModel() {

    private val _ordersEvent = MutableStateFlow<OrdersEvent>(OrdersEvent.Loading)
    val ordersEvent = _ordersEvent.asStateFlow()
    val userDomainModel  = ShopperSession.getUser()

    init {
        getOrderList()
    }

    fun filterOrders(list: List<OrdersData>, filter: String): List<OrdersData> {
        val filteredList = list.filter { it.status == filter }
        return filteredList
    }

    private fun getOrderList() {
        viewModelScope.launch {
            val result = orderListUseCase.execute(userDomainModel!!.id!!.toLong())

            when (result) {
                is com.codewithfk.domain.network.ResultWrapper.Success -> {
                    val data = result.value
                    _ordersEvent.value = OrdersEvent.Success(data.`data`)
                }

                is com.codewithfk.domain.network.ResultWrapper.Failure -> {
                    _ordersEvent.value = OrdersEvent.Error("Something went wrong")
                }
            }

        }
    }
}

sealed class OrdersEvent {
    object Loading : OrdersEvent()
    data class Success(val data: List<OrdersData>) : OrdersEvent()
    data class Error(val errorMsg: String) : OrdersEvent()
}