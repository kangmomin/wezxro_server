package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.controller.response.ServiceDetailResponse
import com.hwalaon.wezxro_server.domain.service.service.user.CommandUserServiceService
import com.hwalaon.wezxro_server.domain.service.service.user.QueryUserServiceService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/s")
class ServiceController(
    private val commandUserServiceService: CommandUserServiceService,
    private val queryUserServiceService: QueryUserServiceService
) {

    @GetMapping("/{id}")
    fun serviceDetail(@PathVariable id: Int) =
        queryUserServiceService.serviceDetail(id).let {
            BasicResponse.ok(
                ServiceDetailResponse.fromDomain(it))
        }
}