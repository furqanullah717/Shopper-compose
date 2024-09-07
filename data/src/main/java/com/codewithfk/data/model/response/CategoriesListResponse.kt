package com.codewithfk.data.model.response

import com.codewithfk.data.model.CategoryDataModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesListResponse(
    val `data`: List<CategoryDataModel>,
    val msg: String
) {
    fun toCategoriesList() = com.codewithfk.domain.model.CategoriesListModel(
        categories = `data`.map { it.toCategory() },
        msg = msg
    )
}