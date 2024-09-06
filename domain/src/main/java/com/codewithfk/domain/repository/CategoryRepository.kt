package com.codewithfk.domain.repository

import com.codewithfk.domain.model.response.CategoryResponse
import com.codewithfk.domain.network.ResultWrapper

interface CategoryRepository {
    suspend fun getCategories(): ResultWrapper<CategoryResponse>
}