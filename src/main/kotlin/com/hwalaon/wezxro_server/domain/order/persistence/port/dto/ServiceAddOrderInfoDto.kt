package com.hwalaon.wezxro_server.domain.order.persistence.port.dto

import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.querydsl.core.annotations.QueryProjection

class ServiceAddOrderInfoDto @QueryProjection constructor (
    val min: Long,
    val max: Long,
    val type: ServiceType,
    val apiServiceId: Long,
)