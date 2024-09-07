package com.codewithfk.data.model.response

import com.codewithfk.data.model.DataProductModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val `data`: List<DataProductModel>,
    val msg: String
) {
    fun toProductList() = com.codewithfk.domain.model.ProductListModel(
        products = `data`.map { it.toProduct() },
        msg = msg
    )
}