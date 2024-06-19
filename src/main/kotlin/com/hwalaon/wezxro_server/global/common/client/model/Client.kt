package com.hwalaon.wezxro_server.global.common.client.model

import java.util.*

data class Client (
    var id: UUID?,
    var domain: String?,
    var email: String?,
    var password: String?,
    var host: String?,
) {
    constructor(
        domain: String?,
        email: String?,
        password: String?,
        host: String?
    ) : this(
        id = null,
        domain = domain,
        email = email,
        password = password,
        host = host
    )
}