package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.controller.response.ServiceDetailResponse
import com.hwalaon.wezxro_server.domain.service.service.user.CommandUserServiceService
import com.hwalaon.wezxro_server.domain.service.service.user.QueryUserServiceService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/s")
class ServiceController(
    private val commandUserServiceService: CommandUserServiceService,
    private val queryUserServiceService: QueryUserServiceService
) {

    @GetMapping("/detail/{id}")
    fun serviceDetail(
        @PathVariable id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ) = queryUserServiceService.serviceDetail(id, principalDetails.account).let {
            BasicResponse.ok(
                ServiceDetailResponse.fromDomain(it))
        }

    @GetMapping("/list")
    fun serviceDetailList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestParam(name = "category") category: Long?
    ) = queryUserServiceService.serviceDetailList(principalDetails.account, category).let {
            BasicResponse.ok(
                it.map { service ->
                    ServiceDetailResponse.fromDomain(service)
                })
        }
}