package com.hwalaon.wezxro_server.domain.service.controller.response

import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType

data class ServiceDetailResponse(
    val name: String,
    val min: Long,
    val max: Long,
    val rate: Double,
    val description: String,
    val serviceId: Long,
    val type: ServiceType,
) {
    companion object {
        fun fromDomain(domain: Service) =
            ServiceDetailResponse(
                name = domain.name!!,
                min = domain.min!!,
                max = domain.max!!,
                rate = domain.rate!!,
                description = domain.description!!,
                serviceId = domain.id!!,
                type = domain.type!!
            )
    }
}
