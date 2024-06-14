package com.hwalaon.wezxro_server.domain.wapi.persistence

import com.hwalaon.wezxro_server.domain.order.controller.request.AddOrderRequest
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ProviderApiDto
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.wapi.controller.request.WapiAddOrderRequest
import com.hwalaon.wezxro_server.domain.wapi.controller.response.OrderStatusResponse
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiInvalidKeyException
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.*
import com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto.WapiServiceDto
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WapiPersistence(
    private val wapiServicePort: WapiServicePort,
    private val wapiCategoryPort: WapiCategoryPort,
    private val wapiAccountPort: WapiAccountPort,
    private val wapiOrderPort: WapiOrderPort,
    private val wapiProviderPort: WapiProviderPort
) {
    fun services(clientId: UUID): MutableList<WapiServiceDto> {
        return wapiServicePort.getServices(clientId)
    }

    fun categoryNamesByIds(categoryIds: MutableList<Long>) =
        wapiCategoryPort.categoryNamesByIds(categoryIds)

    fun clientIdByKey(key: String) =
        wapiAccountPort.getClientIdByUserKey(key)

    fun balance(key: String): Result<Double?> {
        val balance = wapiAccountPort.getUserBalanceByKey(key)
            ?: return Result.failure(Error("not found"))

        return Result.success(balance)
    }

    fun serviceInfo(serviceId: Long) =
        wapiServicePort.serviceInfo(serviceId)

    fun addOrder(key: String, orderData: WapiAddOrderRequest, serviceInfo: Service, providerInfo: ProviderApiDto): Long {
        val order = AddOrderRequest(
            categoryId = serviceInfo.categoryId,
            serviceId = serviceInfo.id,
            link = orderData.link,
            count = orderData.quantity,
            totalCharge = (serviceInfo.rate!! * orderData.quantity!!),
            comments = orderData.comments,
            commentsCustomPackage = orderData.commentsCustomPackage,
            hashtag = orderData.hashtag,
            hashtags = orderData.hashtags,
            answerNumber = orderData.answerNumber,
            expiry = orderData.expiry,
            groups = orderData.groups,
            mediaUrl = orderData.mediaUrl,
            subDelay = orderData.subDelay,
            subMax = orderData.subMax,
            subMin = orderData.subMin,
            subPosts = orderData.subPosts,
            subUsername = orderData.subUsername,
            username = orderData.username,
            usernames = orderData.usernames,
            usernamesCustom = orderData.usernamesCustom
        ).toDomain()

        order.type = serviceInfo.type
        order.userId = wapiAccountPort.getUserIdByKey(key)
        order.providerId = serviceInfo.providerId
        order.info!!.order = order

        return wapiOrderPort.addOrder(key, order, serviceInfo.apiServiceId!!, providerInfo)
    }

    fun getProviderInfo(providerId: Long) = wapiProviderPort.getProviderInfoById(providerId)
    fun getOrderStatus(key: String, orders: List<Long>): List<OrderStatusResponse> {
        val userId = (wapiAccountPort.getUserIdByKey(key)
            ?: throw WapiInvalidKeyException())

        wapiOrderPort.updateOrderStatus(userId)

        return wapiOrderPort.orderStatusList(orders)
    }
}