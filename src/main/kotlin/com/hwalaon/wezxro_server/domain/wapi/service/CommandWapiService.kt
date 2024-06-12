package com.hwalaon.wezxro_server.domain.wapi.service

import com.hwalaon.wezxro_server.domain.wapi.controller.response.ServiceResponse
import com.hwalaon.wezxro_server.domain.wapi.exception.WapiInvalidKeyException
import com.hwalaon.wezxro_server.domain.wapi.persistence.WapiPersistence
import com.hwalaon.wezxro_server.global.annotation.CommandService

@CommandService
class CommandWapiService(
    private val wapiPersistence: WapiPersistence
) {


}