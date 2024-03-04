package com.hwalaon.wezxro_server.domain.order.persistence.port.dto

import com.querydsl.core.annotations.QueryProjection

data class ProviderApiDto @QueryProjection constructor (
    val apiKey: String,
    val apiUrl: String
)
