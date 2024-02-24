package com.hwalaon.wezxro_server.domain.service.model

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

class Service (
    var id: Long?,
    var clientId: UUID?,
    var providerId: Int?,
    var categoryId: Int?,
    var apiServiceId: Int?,
    var name: String?,
    var type: String?,
    var rate: Float?,
    var min: Int?,
    var max: Int?,
    var description: String?,
    var status: BasicStatus?,
    var originalRate: Float?
): BasicTime() {
    companion object {
        fun serviceDetail(serviceId: Long, rate: Float, name: String, description: String, min: Int, max: Int) =
            Service(
                id = serviceId,
                rate = rate,
                name = name,
                description = description,
                max = max,
                min = min,
                status = null,
                type = null,
                originalRate = null,
                clientId = null,
                categoryId = null,
                providerId = null,
                apiServiceId = null,
            )
    }
}