package com.hwalaon.wezxro_server.domain.service.model

import com.hwalaon.wezxro_server.global.constant.BasicStatus

class Service (
    var id: Int?,
    var providerId: Int?,
    var categoryId: Int?,
    var apiServiceId: Int?,
    var name: String?,
    var type: String,
    var rate: Float?,
    var min: Int?,
    var max: Int?,
    var description: String?,
    var status: BasicStatus?,
    var originalRate: Float?
)