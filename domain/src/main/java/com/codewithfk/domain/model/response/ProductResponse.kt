package com.codewithfk.domain.model.response

import com.codewithfk.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val data: List<Product>,
    val msg: String
)