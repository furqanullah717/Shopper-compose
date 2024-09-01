package com.codewithfk.domain.repository

import com.codewithfk.domain.network.ResultWrapper

interface CategoryRepository {
    suspend fun getCategories(): ResultWrapper<List<String>>
}