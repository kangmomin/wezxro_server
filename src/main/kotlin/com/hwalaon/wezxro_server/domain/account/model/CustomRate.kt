package com.hwalaon.wezxro_server.domain.account.model

import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime

class CustomRate (
    var id: Long?,
    var serviceId: Long?,
    var user: Account?,
    var rate: Float
): BasicTime()