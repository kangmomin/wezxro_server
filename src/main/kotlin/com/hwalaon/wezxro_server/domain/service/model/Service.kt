package com.hwalaon.wezxro_server.domain.service.model

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

class Service (
    var id: Int?,
    var clientId: UUID?,
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
): BasicTime()