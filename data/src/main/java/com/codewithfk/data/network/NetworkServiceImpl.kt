package com.codewithfk.data.network

import com.codewithfk.data.model.DataProductModel
import com.codewithfk.domain.model.CartItem
import com.codewithfk.domain.model.Product
import com.codewithfk.domain.model.request.AddToCartRequest
import com.codewithfk.domain.model.response.CartResponse
import com.codewithfk.domain.model.response.CategoryResponse
import com.codewithfk.domain.model.response.ProductResponse
import com.codewithfk.domain.network.NetworkService
import com.codewithfk.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException

class NetworkServiceImpl(val client: HttpClient) : NetworkService {
    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com"
    override suspend fun getProducts(category: Int?): ResultWrapper<ProductResponse> {
        val url =
            if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products/category/1"

        return makeWebRequest<ProductResponse, ProductResponse>(
            url = url,
            method = HttpMethod.Get
        )
    }

    override suspend fun getCategories(): ResultWrapper<CategoryResponse> {
        val url = "$baseUrl/categories"
        return makeWebRequest<CategoryResponse, CategoryResponse>(
            url = url,
            method = HttpMethod.Get,
        )
    }

    override suspend fun getCart(userId: Int): ResultWrapper<CartResponse> {
        val url = "$baseUrl/cart/$userId"
        return makeWebRequest<CartResponse, CartResponse>(
            url = url,
            method = HttpMethod.Get
        )
    }

    override suspend fun updateQuantity(
        cartItem: CartItem,
        userId: Int
    ): ResultWrapper<CartResponse> {
        val url = "$baseUrl/cart/$userId/${cartItem.id}"
        return makeWebRequest<CartResponse, CartResponse>(
            url = url,
            method = HttpMethod.Put,
            body = AddToCartRequest(
                id = cartItem.id,
                productId = cartItem.productId,
                productName = cartItem.productName,
                price = cartItem.price,
                quantity = cartItem.quantity,
                userId = userId
            )
        )
    }

    override suspend fun removeProductFromCart(
        cartItemId: Int,
        userId: Int
    ): ResultWrapper<CartResponse> {
        val url = "$baseUrl/cart/$userId/$cartItemId"
        return makeWebRequest<CartResponse, CartResponse>(
            url = url,
            method = HttpMethod.Delete
        )
    }

    override suspend fun addProductToCart(
        product: Product,
        userId: Int
    ): ResultWrapper<CartResponse> {
        val url = "$baseUrl/cart/$userId"
        return makeWebRequest<CartResponse, CartResponse>(
            url = url,
            method = HttpMethod.Post,
            body = AddToCartRequest(
                productId = product.id.toInt(),
                productName = product.title,
                price = product.price,
                quantity = 1,
                userId = userId
            )
        )
    }

    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method
                // Apply query parameters
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }
                // Apply headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                // Set body for POST, PUT, etc.
                if (body != null) {
                    setBody(body)
                }

                // Set content type
                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }

}