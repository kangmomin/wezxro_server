package com.hwalaon.wezxro_server.domain.wapi.persistence.port.dto

import com.querydsl.core.annotations.QueryProjection

class WapiCategoryDto
@QueryProjection constructor (
    val categoryName: String,
    val categoryId: Long
)