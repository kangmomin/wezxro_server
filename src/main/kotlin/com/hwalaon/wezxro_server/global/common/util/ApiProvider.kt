package com.hwalaon.wezxro_server.global.common.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.hwalaon.wezxro_server.global.common.exception.ApiRequestFailedException
import com.hwalaon.wezxro_server.global.common.exception.ApiServerException
import com.hwalaon.wezxro_server.global.common.util.request.AddOrderInfoRequestDto
import com.hwalaon.wezxro_server.global.common.util.request.CancelOrderRequestDto
import com.hwalaon.wezxro_server.global.common.util.request.OrderRequestDto
import com.hwalaon.wezxro_server.global.common.util.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
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
    )

    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private fun fetchApi(action: String,
                         params: String = json.encodeToString(ApiRequest(apiKey, action))
    ): String {
        // JSON 문자열을 Map으로 변환
        val paramMap: Map<String, String> = Gson().fromJson(params, Map::class.java) as Map<String, String>

        // URL에 쿼리 파라미터 추가
        val urlBuilder = apiUrl.toHttpUrlOrNull()?.newBuilder()
        paramMap.forEach { (key, value) ->
            urlBuilder?.addQueryParameter(key, value)
        }

        // 완성된 URL
        val urlWithParams = urlBuilder?.build().toString()

        val request = Request.Builder()
                .url(urlWithParams)
                .post("".toRequestBody())
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

    fun createSingleCancel(orderId: Long): CancelOrderResponseDto {

        val res = fetchApi("cancel", json.encodeToString(
                CancelOrderRequestDto.serializer(),
                CancelOrderRequestDto(
                    key = apiKey,
                    action = "cancel",
                    order = orderId.toString()
                )
            ))

        return Gson().fromJson(res, CancelOrderResponseDto::class.java)
    }

    fun addOrder(addOrderInfoRequestDto: AddOrderInfoRequestDto): OrderIdDto {
        addOrderInfoRequestDto.key = this.apiKey
        addOrderInfoRequestDto.action = "add"

        val response =
            fetchApi("add", json.encodeToString(AddOrderInfoRequestDto.serializer(), addOrderInfoRequestDto))

        return Gson().fromJson(response, OrderIdDto::class.java) ?: throw ApiRequestFailedException()
    }

    fun getOrderStatus(order: Int): String {
        return fetchApi("status", )
    }

    fun getMultipleOrdersStatus(orders: List<Long>): Map<String, MultipleOrderStatus> {
        val response = fetchApi("status", json.encodeToString(
            OrderRequestDto.serializer(),
            OrderRequestDto(
                orders = orders.joinToString(","),
                key = this.apiKey,
                action = "status"
            )
        ))
        val type: Type = object : TypeToken<Map<String, MultipleOrderStatus>>() {}.type
        return Gson().fromJson(response, type) ?: throw ApiRequestFailedException()
    }

    fun createRefill(order: Int): String {
        return fetchApi("refill")
    }

    fun createMultipleRefills(orders: List<Int>): String {
        return fetchApi("refill")
    }

    fun getRefillStatus(refill: Int): String {
        return fetchApi("refill_status")
    }

    fun getMultipleRefillStatus(refills: List<Int>): String {
        return fetchApi("refill_status")
    }

    fun getUserBalance(): UserBalanceDto {
        val responseString = fetchApi("balance")
        return Gson().fromJson(responseString, UserBalanceDto::class.java) ?: throw ApiRequestFailedException()
    }
}
