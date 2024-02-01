package com.hwalaon.wezxro_server.domain.category.controller.request

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class SaveCategoryRequest (
    @NotEmpty
    val name: String,

    @NotEmpty
    val status: BasicStatus,

    @NotNull
    val sort: Int,

    @NotNull
    val clientId: UUID
) {
    fun toDomain() =
        this.let {
            Category(
                name = it.name,
                status = it.status,
                id = null,
                sort = it.sort,
                clientId = it.clientId
            )
        }
}
