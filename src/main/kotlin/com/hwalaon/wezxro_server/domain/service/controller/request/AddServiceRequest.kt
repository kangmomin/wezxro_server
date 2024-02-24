package com.hwalaon.wezxro_server.domain.service.controller.request

import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotNull
import java.util.*

data class AddServiceRequest (

    @NotNull
    val providerId: Int,

    @NotNull
    val categoryId: Int,

    @NotNull
    val apiServiceId: Int,

    @NotNull
    val name: String,

    @NotNull
    val type: String,

    @NotNull
    val rate: Float,

    @NotNull
    val status: BasicStatus,

    @NotNull
    val min: Int,

    @NotNull
    val max: Int,

    @NotNull
    val description: String,

    @NotNull
    val originalRate: Float
) {
    fun toDomain(clientId: UUID) =
        Service(
            id = null,
            clientId = clientId,
            providerId = this.providerId,
            categoryId = this.categoryId,
            apiServiceId = this.apiServiceId,
            name = this.name,
            type = this.type,
            rate = this.rate,
            min = this.min,
            max = this.max,
            description = this.description,
            status = this.status,
            originalRate = this.originalRate
        )
}