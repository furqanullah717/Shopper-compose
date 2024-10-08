package com.codewithfk.data.model.response

import com.codewithfk.domain.model.CartItemModel
import kotlinx.serialization.Serializable

@Serializable
class CartItem(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String? = null,
    val quantity: Int,
    val productName: String
) {
    fun toCartItemModel(): CartItemModel {
        return CartItemModel(
            id = id,
            productId = productId,
            price = price,
            imageUrl = imageUrl,
            quantity = quantity,
            productName = productName
        )
    }
}