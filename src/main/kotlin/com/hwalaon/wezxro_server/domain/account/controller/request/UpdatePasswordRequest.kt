package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.NotNull

class UpdatePasswordRequest (
    @field: NotNull(message = "새로 설정할 비밀번호가 필요합니다.")
    val newPassword: String?,
    @field: NotNull(message = "관리자 비밀번호가 필요합니다.")
    val adminPassword: String?,
    @field: NotNull(message = "타겟 유저의 아이디가 필요합니다.")
    val userId: Long
)