package com.hwalaon.wezxro_server.dashboard.persistence.port.dto

import com.querydsl.core.annotations.QueryProjection

class AccountPayInfoDto
@QueryProjection constructor(
    val balance: Double,
)
