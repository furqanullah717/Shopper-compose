package com.codewithfk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val categoryId: Int?,
    val description: String,
    val image: String
) : Parcelable {
    val priceString: String
        get() = "$$price"
}
