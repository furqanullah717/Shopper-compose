package com.codewithfk.domain.di

import com.codewithfk.domain.usecase.AddToCartUseCase
import com.codewithfk.domain.usecase.CartSummaryUseCase
import com.codewithfk.domain.usecase.DeleteProductUseCase
import com.codewithfk.domain.usecase.GetCartUseCase
import com.codewithfk.domain.usecase.GetCategoriesUseCase
import com.codewithfk.domain.usecase.GetProductUseCase
import com.codewithfk.domain.usecase.OrderListUseCase
import com.codewithfk.domain.usecase.PlaceOrderUseCase
import com.codewithfk.domain.usecase.UpdateQuantityUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { GetCartUseCase(get()) }
    factory { UpdateQuantityUseCase(get()) }
    factory { DeleteProductUseCase(get()) }
    factory { CartSummaryUseCase(get()) }
    factory { PlaceOrderUseCase(get()) }
    factory { OrderListUseCase(get()) }
}