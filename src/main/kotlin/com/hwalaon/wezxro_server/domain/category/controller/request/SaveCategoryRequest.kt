package com.hwalaon.wezxro_server.domain.category.controller.request

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SaveCategoryRequest (
    @field: NotEmpty
    val name: String?,

    @field: NotNull
    val status: BasicStatus?,

    @field: NotNull
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
