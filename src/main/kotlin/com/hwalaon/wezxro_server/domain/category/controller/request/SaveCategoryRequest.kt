package com.hwalaon.wezxro_server.domain.category.controller.request

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SaveCategoryRequest (
    @field: NotEmpty(message = "카테고리 이름이 필요합니다.")
    val name: String?,

    @field: NotNull(message = "상태 값이 필요합니다.")
    val status: BasicStatus?,

    @field: NotNull(message = "정렬 값이 필요합니다.")
    val sort: Int?,
) {
    fun toDomain() =
        this.let {
            Category(
                name = it.name,
                status = it.status,
                id = null,
                sort = it.sort,
                clientId = null
            )
        }
}
