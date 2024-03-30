package com.hwalaon.wezxro_server.domain.service.controller.request

import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.*

data class AddServiceRequest (

    @field:NotNull(message = "도매처 아이디는 필수 입력 값 입니다.")
    val providerId: Long?,

    @field:NotNull(message = "카테고리 아이디는 필수 입력 값 입니다.")
    val categoryId: Long?,

    @field:NotNull(message = "도매처의 서비스 아이디는 필수 입력 값 입니다.")
    val apiServiceId: Long?,

    @field:NotEmpty(message = "이름은 필수 입력 값 입니다.")
    val name: String?,

    @field:NotNull(message = "서비스 타입은 필수 입력 값 입니다.")
    val type: ServiceType?,

    @field:NotNull(message = "서비스 가격은 필수 입력 값 입니다.")
    val rate: Double?,

    @field:NotNull(message = "Status는 필수 입력 값 입니다.")
    val status: BasicStatus?,

    @field:NotNull(message = "최소 주문 양은 필수 입력 값 입니다.")
    val min: Long?,

    @field:NotNull(message = "최대 주문 양은 필수 입력 값 입니다.")
    val max: Long?,

    @field:NotEmpty(message = "서비스 설명 글은 필수 입력 값 입니다.")
    val description: String?,

    @field:NotNull(message = "도매처 서비스의 비용은 필수 입력 값 입니다.")
    val originalRate: Double?
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