package com.hwalaon.wezxro_server.domain.service.controller.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.querydsl.core.annotations.QueryProjection

data class ServiceListResponse @QueryProjection constructor (
    @JsonProperty("serviceId")
    val id: Long,
    val name: String,
    val providerId: Long,
    val apiServiceId: Long,
    val categoryId: Long,
    val originalRate: Double,
    val type: ServiceType,
    val rate: Double,
    val min: Long,
    val max: Long,
    val description: String,
    val status: Int,
)
