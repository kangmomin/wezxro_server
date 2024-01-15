package com.hwalaon.wezxro_server.domain.category.model

import com.hwalaon.wezxro_server.global.common.model.BasicTime
import com.hwalaon.wezxro_server.global.constant.BasicStatus

class Category (
    var id: Long?,
    var name: String?,
    var sort: Int?,
    var status: BasicStatus?
): BasicTime()