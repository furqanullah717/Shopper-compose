package com.codewithfk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Int,
    val image: String,
    val title: String
)