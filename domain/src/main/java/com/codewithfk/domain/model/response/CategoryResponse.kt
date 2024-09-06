package com.codewithfk.domain.model.response

import com.codewithfk.domain.model.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val data: List<CategoryModel>,
    val msg: String
)