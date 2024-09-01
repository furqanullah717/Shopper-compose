package com.codewithfk.shopper.navigation

import com.codewithfk.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(val product: Product)