package com.hwalaon.wezxro_server.domain.provider.controller.response

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus

class ProviderDetailResponse (
    val providerId: Long,
    val status: BasicStatus,
    val name: String,
    val apiKey: String,
    val apiUrl: String,
    val balance: Double
)