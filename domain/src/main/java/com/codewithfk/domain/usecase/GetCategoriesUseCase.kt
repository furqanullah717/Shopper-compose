package com.codewithfk.domain.usecase

import com.codewithfk.domain.repository.CategoryRepository

class GetCategoriesUseCase (private val repository: CategoryRepository) {
    suspend fun execute() = repository.getCategories()
}