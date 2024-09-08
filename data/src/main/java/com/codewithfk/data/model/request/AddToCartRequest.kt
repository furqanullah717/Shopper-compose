package com.codewithfk.data.model.request

import com.codewithfk.domain.model.request.AddCartRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest(
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int, // Link cart item to the user
) {
    companion object {
        fun fromCartRequestModel(addCartRequestModel: AddCartRequestModel) = AddToCartRequest(
            productId = addCartRequestModel.productId,
            productName = addCartRequestModel.productName,
            price = addCartRequestModel.price,
            quantity = addCartRequestModel.quantity,
            userId = addCartRequestModel.userId
        )
    }
}