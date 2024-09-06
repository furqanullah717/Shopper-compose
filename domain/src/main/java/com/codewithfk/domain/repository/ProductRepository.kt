package com.codewithfk.domain.repository

import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.response.ProductResponse
import com.codewithfk.domain.network.ResultWrapper

interface ProductRepository {
    suspend fun getProducts(category:Int?): ResultWrapper<ProductResponse>
}