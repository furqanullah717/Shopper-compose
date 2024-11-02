package com.codewithfk.shopper.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.codewithfk.shopper.model.UiProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64


val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UiProductModel? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            return bundle.getParcelable(key, UiProductModel::class.java)
        return bundle.getParcelable(key) as? UiProductModel
    }

    override fun parseValue(value: String): UiProductModel {
        val item = Json.decodeFromString<UiProductModel>(value)

        return item.copy(
            image = URLDecoder.decode(item.image, "UTF-8"),
            description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
            title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
        )
    }val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): UiProductModel? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                return bundle.getParcelable(key, UiProductModel::class.java)
            return bundle.getParcelable(key) as? UiProductModel
        }

        override fun parseValue(value: String): UiProductModel {
            val item = Json.decodeFromString<UiProductModel>(value)

            return item.copy(
                image = URLDecoder.decode(item.image, "UTF-8"),
                description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
                title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))
            )
        }

        override fun serializeAsValue(value: UiProductModel): String {
            return Json.encodeToString(
                value.copy(
                    image = URLEncoder.encode(value.image, "UTF-8"),
                    description = String(
                        Base64.getEncoder().encode(value.description.toByteArray())
                    ).replace("/", "_"),
                    title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace(
                        "/",
                        "_"
                    )
                )
            )
        }

        override fun put(bundle: Bundle, key: String, value: UiProductModel) {
            bundle.putParcelable(key, value)
        }

    }

    override fun serializeAsValue(value: UiProductModel): String {
        return Json.encodeToString(
            value.copy(
                image = URLEncoder.encode(value.image, "UTF-8"),
                description = String(
                    Base64.getEncoder().encode(value.description.toByteArray())
                ).replace("/", "_"),
                title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace(
                    "/",
                    "_"
                )
            )
        )
    }

    override fun put(bundle: Bundle, key: String, value: UiProductModel) {
        bundle.putParcelable(key, value)
    }

}