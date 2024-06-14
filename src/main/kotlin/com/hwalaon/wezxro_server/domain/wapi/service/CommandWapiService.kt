package com.hwalaon.wezxro_server.domain.wapi.service

import com.hwalaon.wezxro_server.domain.wapi.controller.request.WapiAddOrderRequest
import com.hwalaon.wezxro_server.domain.wapi.controller.response.ServiceResponse
import com.hwalaon.wezxro_server.domain.wapi.controller.response.WapiErrorResponse
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiBasicException
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiInvalidKeyException
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiInvalidServiceIdException
import com.hwalaon.wezxro_server.domain.wapi.persistence.WapiPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService

@CommandService
class CommandWapiService(
    private val wapiPersistence: WapiPersistence
) {
    fun addOrder(key: String, orderData: WapiAddOrderRequest): Long {
        val serviceInfo = wapiPersistence.serviceInfo(orderData.service!!)
            ?: throw WapiInvalidServiceIdException()

        val providerInfo = wapiPersistence.getProviderInfo(serviceInfo.providerId!!)
            ?: throw WapiBasicException()

        return wapiPersistence.addOrder(key, orderData, serviceInfo, providerInfo)
    }


}