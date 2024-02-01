package com.hwalaon.wezxro_server.domain.category.model

import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime
import java.util.*

class Category (
    var id: Long?,
    var name: String?,
    var sort: Int?,
    var status: BasicStatus?,
    var clientId: UUID?,
): BasicTime()