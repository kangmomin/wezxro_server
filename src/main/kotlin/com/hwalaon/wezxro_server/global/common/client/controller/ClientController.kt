package com.hwalaon.wezxro_server.global.common.client.controller

import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.client.controller.request.AddClientRequest
import com.hwalaon.wezxro_server.global.common.client.controller.response.AddClientResponse
import com.hwalaon.wezxro_server.global.common.client.service.ClientService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/master/c")
class ClientController(
    private val clientService: ClientService
) {

    @PostMapping("/add")
    fun addClient(addClientRequest: AddClientRequest) =
        clientService.addClient(addClientRequest.domain).let {
            BasicResponse.created(
                AddClientResponse(it!!))
        }
}