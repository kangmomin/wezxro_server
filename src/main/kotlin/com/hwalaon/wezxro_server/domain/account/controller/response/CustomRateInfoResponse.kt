package com.hwalaon.wezxro_server.domain.account.controller.response

import com.querydsl.core.annotations.QueryProjection

data class CustomRateInfoResponse
@QueryProjection constructor (
    val id: Long,
    val serviceId: Long,
    var serviceName: String?,
    var originalRate: Double?,
    val customRate: Double
)