package com.hwalaon.wezxro_server.domain.provider.controller

import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderListResponse
import com.hwalaon.wezxro_server.domain.provider.service.CommandProviderService
import com.hwalaon.wezxro_server.domain.provider.service.QueryProviderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/p")
class ProviderController(
    private val queryProviderService: QueryProviderService,
    private val commandProviderService: CommandProviderService
) {

    @GetMapping("/list")
    fun providerList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            queryProviderService.list(principalDetails.account.clientId!!).map {
                ProviderListResponse(
                    id = it.id,
                    status = it.status!!,
                    name = it.name,
                    description = it.description,
                    balance = it.balance!!,
                    apiUrl = it.apiUrl!!
                )
            }
        )

}