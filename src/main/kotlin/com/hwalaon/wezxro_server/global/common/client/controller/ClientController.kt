package com.hwalaon.wezxro_server.global.common.client.controller

import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.client.controller.request.AddClientRequest
import com.hwalaon.wezxro_server.global.common.client.controller.request.UpdateEmailRequest
import com.hwalaon.wezxro_server.global.common.client.controller.response.AddClientResponse
import com.hwalaon.wezxro_server.global.common.client.service.ClientService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/master/c")
class ClientController(
    private val clientService: ClientService
) {

    @PostMapping("/add")
    fun addClient(@RequestBody @Valid addClientRequest: AddClientRequest) =
        clientService.addClient(addClientRequest).let {
            BasicResponse.created(
                AddClientResponse(it!!))
        }

    @PatchMapping("/email")
    fun updateEmailInfo(
        @RequestBody @Valid updateEmailRequest: UpdateEmailRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        clientService.updateEmailInfo(updateEmailRequest)

        return BasicResponse.ok("이메일 정보를 업데이트 하였습니다.")
    }
}