package com.hwalaon.wezxro_server.global.common.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import com.hwalaon.wezxro_server.global.common.exception.ApiServerException
import com.hwalaon.wezxro_server.global.common.util.dto.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
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
        val params: Map<String, String>
    )

    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private fun fetchApi(action: String, params: Map<String, String> = emptyMap(), addOrderInfoDto: String? = null): String {
        // API 요청 객체 생성
        val apiRequest = ApiRequest(apiKey, action, params)

        // JSON 문자열로 직렬화
        val requestBody = json.encodeToString(ApiRequest.serializer(), apiRequest)
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        // apiProvider 의 param 에 어떤 데이터가 들어가는지 확인
        val request = if (action == "add")
            Request.Builder()
                .url(apiUrl)
                .post(addOrderInfoDto!!
                    .toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()
        else
            Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .build()


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw ApiRequestFailedException()
            }
            val apiResponse = response.body?.string() ?: throw ApiRequestFailedException()
            try {
                val errorMessage = Gson().fromJson(apiResponse, ProviderApiErrorDto::class.java).error
                    ?: return apiResponse

                throw ApiServerException("도매처 서버에서 에러가 발생하였습니다. [${errorMessage}]")
            } catch(e: JsonSyntaxException) {
                return apiResponse
            }
        }
    }

    fun getServices(): List<ProviderServiceDto> {
        val response = fetchApi("services")
        val type: Type = object : TypeToken<List<ProviderServiceDto>>() {}.type
        return Gson().fromJson(response, type) ?: throw ApiRequestFailedException()
    }

    fun addOrder(addOrderInfoDto: AddOrderInfoDto): OrderIdDto {
        addOrderInfoDto.key = this.apiKey
        addOrderInfoDto.action = "add"

        val response =
            fetchApi("add", addOrderInfoDto = json.encodeToString(AddOrderInfoDto.serializer(), addOrderInfoDto))
        return Gson().fromJson(response, OrderIdDto::class.java) ?: throw ApiRequestFailedException()
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
