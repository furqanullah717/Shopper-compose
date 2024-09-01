package com.codewithfk.shopper.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.codewithfk.domain.model.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
val ProductDetailNavType = object : NavType<Product>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Product? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Product::class.java)
        } else {
            bundle.getParcelable(key)
        }

    }

    override fun parseValue(value: String): Product {
        val item = Json.decodeFromString<Product>(value)
        return item.copy(
            image = URLDecoder.decode(item.image, StandardCharsets.UTF_8.toString()),
            description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
            title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
        )
    }


    override fun serializeAsValue(value: Product): String {
        val endodedProduce = value.copy(
            image = URLEncoder.encode(
                value.image, StandardCharsets.UTF_8.toString(),
            ),
            description = Base64.getEncoder().encodeToString(
                value.description.toByteArray()
            ).replace("/", "_"),
            title = Base64.getEncoder().encodeToString(
                value.title.toByteArray()
            ).replace("/", "_")
        )
        return Json.encodeToString(endodedProduce)
    }

    override fun put(bundle: Bundle, key: String, value: Product) {
        bundle.putParcelable(key, value)
    }
}