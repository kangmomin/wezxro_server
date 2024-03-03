package com.hwalaon.wezxro_server.global.common.util

import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ApiProvider(
    private val apiKey: String,
    private val apiUrl: String,
    private val isFormData: Boolean = false
) {
    private val client = OkHttpClient()

    private fun createFormData(params: Map<String, String>): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("key", apiKey)
        params.forEach { (key, value) ->
            builder.addFormDataPart(key, value)
        }
        return builder.build()
    }

    private suspend fun fetchApi(action: String, params: Map<String, String> = emptyMap()): String {
        return withContext(Dispatchers.IO) {
            val data = if (isFormData) {
                createFormData(params + ("action" to action))
            } else {
                val json = params + ("key" to apiKey) + ("action" to action)
                // Assuming JSON.stringify equivalent exists or you implement it
                json.toString().toRequestBody()
            }

            val request = Request.Builder()
                .url(apiUrl)
                .post(data)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("response message: ${response.message}")
                    println("response body: ${response.body}")
                    throw ApiRequestFailedException()
                }

                response.body?.string() ?: ""
            }
        }
    }

    suspend fun getServices(): String {
        return fetchApi("services")
    }

    suspend fun addOrder(service: Long, link: String, quantity: Long): String {
        return fetchApi("add", mapOf("service" to service.toString(), "link" to link, "quantity" to quantity.toString()))
    }

    suspend fun getOrderStatus(order: Int): String {
        return fetchApi("status", mapOf("order" to order.toString()))
    }

    suspend fun getMultipleOrdersStatus(orders: List<Int>): String {
        return fetchApi("status", mapOf("orders" to orders.joinToString(",")))
    }

    suspend fun createRefill(order: Int): String {
        return fetchApi("refill", mapOf("order" to order.toString()))
    }

    suspend fun createMultipleRefills(orders: List<Int>): String {
        return fetchApi("refill", mapOf("orders" to orders.joinToString(",")))
    }

    suspend fun getRefillStatus(refill: Int): String {
        return fetchApi("refill_status", mapOf("refill" to refill.toString()))
    }

    suspend fun getMultipleRefillStatus(refills: List<Int>): String {
        return fetchApi("refill_status", mapOf("refills" to refills.joinToString(",")))
    }

    suspend fun getUserBalance(): String {
        return fetchApi("balance")
    }
}
