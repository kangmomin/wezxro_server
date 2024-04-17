package com.hwalaon.wezxro_server.dashboard.persistence.port.dto

import com.querydsl.core.annotations.QueryProjection

class OrderInfosDto
@QueryProjection constructor(
    val apiOrderId: Long,
    val providerId: Long
)