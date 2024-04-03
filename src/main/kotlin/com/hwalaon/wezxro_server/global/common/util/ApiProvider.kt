package com.hwalaon.wezxro_server.global.common.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import com.hwalaon.wezxro_server.global.common.exception.ApiServerException
import com.hwalaon.wezxro_server.global.common.util.dto.ProviderApiErrorDto
import com.hwalaon.wezxro_server.global.common.util.dto.ProviderServiceDto
import com.hwalaon.wezxro_server.global.common.util.dto.UserBalanceDto
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.Type

class ApiProvider(
    private val apiKey: String,
    private val apiUrl: String,
) {
    @Serializable
    private data class ApiRequest(
        val key: String,
        val action: String,
        val params: Map<String, String> = emptyMap()
    )

    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private fun createFormData(params: Map<String, String>): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        builder.addFormDataPart("key", apiKey)
        params.forEach { (key, value) ->
            builder.addFormDataPart(key, value)
        }
        return builder.build()
    }

    private fun fetchApi(action: String, params: Map<String, String> = emptyMap()): String {
        // API 요청 객체 생성
        val apiRequest = ApiRequest(apiKey, action, params)

        // JSON 문자열로 직렬화
        val requestBody = json.encodeToString(ApiRequest.serializer(), apiRequest)
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(apiUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw ApiRequestFailedException()
            }
            val apiResponse = response.body?.string() ?: throw ApiRequestFailedException()
            try {
                Gson().fromJson(apiResponse, ProviderApiErrorDto::class.java)
                throw ApiServerException()
            } catch(e: IllegalStateException) {
                return apiResponse
            }
        }
    }

    fun getServices(): List<ProviderServiceDto> {
        val response = fetchApi("services")
        val type: Type = object : TypeToken<List<ProviderServiceDto>>() {}.type
        return Gson().fromJson(response, type) ?: throw ApiRequestFailedException()
    }

    fun addOrder(service: Long, link: String, quantity: Long): String {
        return fetchApi("add", mapOf("service" to service.toString(), "link" to link, "quantity" to quantity.toString()))
    }

    fun getOrderStatus(order: Int): String {
        return fetchApi("status", mapOf("order" to order.toString()))
    }

    fun getMultipleOrdersStatus(orders: List<Int>): String {
        return fetchApi("status", mapOf("orders" to orders.joinToString(",")))
    }

    fun createRefill(order: Int): String {
        return fetchApi("refill", mapOf("order" to order.toString()))
    }

    fun createMultipleRefills(orders: List<Int>): String {
        return fetchApi("refill", mapOf("orders" to orders.joinToString(",")))
    }

    fun getRefillStatus(refill: Int): String {
        return fetchApi("refill_status", mapOf("refill" to refill.toString()))
    }

    fun getMultipleRefillStatus(refills: List<Int>): String {
        return fetchApi("refill_status", mapOf("refills" to refills.joinToString(",")))
    }

    fun getUserBalance(): UserBalanceDto {
        val responseString = fetchApi("balance")
        return Gson().fromJson(responseString, UserBalanceDto::class.java) ?: throw ApiRequestFailedException()
    }
}
