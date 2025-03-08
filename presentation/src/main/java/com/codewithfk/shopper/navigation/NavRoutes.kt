package com.codewithfk.shopper.navigation

import android.annotation.SuppressLint
import com.codewithfk.shopper.model.UiProductModel
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object CartScreen

@Serializable
object AllProductsScreen

@Serializable
object OrdersScreen

@Serializable
object ProfileScreen

@Serializable
object CartSummaryScreen

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ProductDetails(val product: UiProductModel)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class UserAddressRoute(val userAddressWrapper: UserAddressRouteWrapper)