package com.hwalaon.wezxro_server.global.common.client.controller.request

import jakarta.validation.constraints.NotEmpty

data class AddClientRequest (

    @NotEmpty
    val domain: String
)
