package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.ProductRepository

class GetProductUseCase(private val repository: ProductRepository) {
    suspend fun execute() = repository.getProducts()
}