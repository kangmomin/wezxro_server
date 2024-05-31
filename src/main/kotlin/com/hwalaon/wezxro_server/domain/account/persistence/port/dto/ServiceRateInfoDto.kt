package com.hwalaon.wezxro_server.domain.account.persistence.port.dto

import com.querydsl.core.annotations.QueryProjection

class ServiceRateInfoDto @QueryProjection constructor (
    val serviceId: Long,
    val serviceName: String,
    val originalRate: Double,
)
