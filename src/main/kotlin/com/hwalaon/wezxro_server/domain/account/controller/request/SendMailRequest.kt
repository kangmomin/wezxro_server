package com.hwalaon.wezxro_server.domain.account.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

class SendMailRequest (
    @field:Email(message = "타겟 이메일은 필수로 입력되어야 합니다.")
    val email: String?,

    @field:NotEmpty(message = "제목이 필요합니다.")
    val subject: String?,

    @field:NotEmpty(message = "내용이 필요합니다.")
    val description: String?
)