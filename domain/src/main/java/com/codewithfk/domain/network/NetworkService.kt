package com.codewithfk.domain.network

import com.codewithfk.domain.model.CategoriesListModel
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.ProductListModel

interface NetworkService {
    suspend fun getProducts(category:Int?): ResultWrapper<ProductListModel>
    suspend fun getCategories(): ResultWrapper<CategoriesListModel>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}