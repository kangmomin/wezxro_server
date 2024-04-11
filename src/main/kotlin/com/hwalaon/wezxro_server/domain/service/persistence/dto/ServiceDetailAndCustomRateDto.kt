package com.hwalaon.wezxro_server.domain.service.persistence.dto

import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.querydsl.core.annotations.QueryProjection

data class ServiceDetailAndCustomRateDto
@QueryProjection constructor(
    val serviceId: Long,
    val name: String,
    val rate: Double,
    val min: Long,
    val max: Long,
    val description: String,
    val customRate: Double?,
    val type: ServiceType,
)
