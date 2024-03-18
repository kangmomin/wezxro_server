package com.hwalaon.wezxro_server.domain.category.controller.request

import com.fasterxml.jackson.annotation.JsonCreator
import jakarta.validation.constraints.NotNull

data class CategorySortRequest @JsonCreator constructor (
    @field: NotNull(message = "정렬 값을 필수 값입니다.")
    var sort: Int?
)
