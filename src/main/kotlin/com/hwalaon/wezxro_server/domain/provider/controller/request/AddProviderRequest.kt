package com.hwalaon.wezxro_server.domain.provider.controller.request

import com.hwalaon.wezxro_server.domain.provider.model.Provider
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AddProviderRequest (
    @field: NotEmpty(message = "도매처 이름은 필수 값 입니다.")
    val name: String?,
    val description: String?,
    @field: NotEmpty(message = "도매처 API 키는 필수 값 입니다.")
    val apiKey: String?,
    @field: NotEmpty(message = "도매처 URL은 필수 값 입니다.")
    val apiUrl: String?,
    @field: NotNull(message = "도매처 상태 값은 필수 값 입니다.")
    val status: BasicStatus?,
) {
    fun toDomain() =
        Provider(
            id = null,
            type = true,
            userId = null,
            status = status,
            description = description,
            name = name,
            balance = null,
            apiKey = apiKey,
            apiUrl = apiUrl,
            clientId = null,
        )
}
