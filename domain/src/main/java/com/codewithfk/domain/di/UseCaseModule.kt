package com.codewithfk.domain.di

import com.codewithfk.domain.usecase.AddProductToCartUseCase
import com.codewithfk.domain.usecase.GetCartUseCase
import com.codewithfk.domain.usecase.GetCategoriesUseCase
import com.codewithfk.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetProductUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { AddProductToCartUseCase(get()) }
    factory { GetCartUseCase(get()) }
}