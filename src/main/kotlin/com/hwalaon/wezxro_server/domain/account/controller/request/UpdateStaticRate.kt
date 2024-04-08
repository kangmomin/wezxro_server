package com.hwalaon.wezxro_server.domain.account.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

class UpdateStaticRate (

    @field: NotNull(message = "전체 감가액은 필수입니다.")
    @JsonProperty("static_rate")
    val staticRate: Double?
)