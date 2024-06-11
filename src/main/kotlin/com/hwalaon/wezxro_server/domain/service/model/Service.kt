package com.hwalaon.wezxro_server.domain.service.model

import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

class Service (
    var id: Long?,
    var clientId: UUID?,
    var providerId: Long?,
    var categoryId: Long?,
    var apiServiceId: Long?,
    var name: String?,
    var type: ServiceType?,
    var rate: Double?,
    var min: Long?,
    var max: Long?,
    var cancel: Boolean?,
    var refill: Boolean?,
    var description: String?,
    var status: BasicStatus?,
    var originalRate: Double?
): BasicTime() {
    companion object {
        fun serviceDetail(serviceId: Long, rate: Double, name: String, description: String, min: Long, max: Long, type: ServiceType) =
            Service(
                id = serviceId,
                rate = rate,
                name = name,
                description = description,
                max = max,
                min = min,
                type = type,
                status = null,
                originalRate = null,
                clientId = null,
                categoryId = null,
                providerId = null,
                apiServiceId = null,
                cancel = null,
                refill = null
            )
    }
}