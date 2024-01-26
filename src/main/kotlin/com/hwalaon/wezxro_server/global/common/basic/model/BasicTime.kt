package com.hwalaon.wezxro_server.global.common.model

import java.time.LocalDateTime

abstract class BasicTime {
    var updatedAt: LocalDateTime? = null
    var createdAt: LocalDateTime? = null
}