package com.hwalaon.wezxro_server.global.common.client.model

import java.util.*

data class Client (
    var id: UUID?,
    var domain: String?,
) {
    constructor(
        domain: String?
    ) : this(id = null, domain = domain)
}