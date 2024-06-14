package com.hwalaon.wezxro_server.domain.wapi.service

import com.hwalaon.wezxro_server.domain.wapi.controller.response.ServiceResponse
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiInvalidKeyException
import com.hwalaon.wezxro_server.domain.wapi.persistence.WapiPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService

@ReadOnlyService
class QueryWapiService(
    private val wapiPersistence: WapiPersistence
) {
    fun userBalance(key: String): Double {
        val balance = wapiPersistence.balance(key).onFailure {
            when (it.message) {
                "not found" -> throw WapiInvalidKeyException()
            }
        }

        return balance.getOrNull()!!
    }
    fun services(key: String): List<ServiceResponse> {
        val clientId = wapiPersistence.clientIdByKey(key) ?: throw WapiInvalidKeyException()
        val serviceDtoList = wapiPersistence.services(clientId)
        val categoryIds = serviceDtoList.map { it.categoryId }
        val categoryNames = wapiPersistence.categoryNamesByIds(categoryIds.toMutableList())

        return serviceDtoList.map {
            val categoryName = categoryNames.find { c ->
                it.categoryId == c.categoryId
            }

            return@map ServiceResponse(
                service = it.id,
                name = it.name,
                type = it.type,
                min = it.min,
                max = it.max,
                rate = it.rate,
                cancel = it.cancel,
                category = categoryName?.categoryName ?: "DEFAULT",
                refill = it.refill
            )
        }
    }
}