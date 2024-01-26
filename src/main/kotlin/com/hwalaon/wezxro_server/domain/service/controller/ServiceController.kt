package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.service.ServiceService
import com.hwalaon.wezxro_server.global.common.BasicResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/s")
class ServiceController(
    private val serviceService: ServiceService
) {

    @GetMapping("/{id}")
    fun serviceDetail(@PathVariable id: Int) =
        serviceService.serviceDetail(id).let {
            BasicResponse.ok(it)
        }
}