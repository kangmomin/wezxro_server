package com.hwalaon.wezxro_server.domain.provider.controller

import com.hwalaon.wezxro_server.domain.provider.controller.request.AddProviderRequest
import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderListResponse
import com.hwalaon.wezxro_server.domain.provider.service.CommandProviderService
import com.hwalaon.wezxro_server.domain.provider.service.QueryProviderService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/p")
class ProviderAdminController(
    private val queryProviderService: QueryProviderService,
    private val commandProviderService: CommandProviderService
) {

    @PostMapping("/add")
    fun addProvider(
        @RequestBody @Valid addProviderRequest: AddProviderRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val provider = addProviderRequest.toDomain()
        provider.userId = principalDetails.account.userId!!
        provider.clientId = principalDetails.account.clientId!!

        commandProviderService.addProvider(provider)
        return BasicResponse.ok("도매처가 등록되었습니다.")
    }

    @PostMapping("/status/{providerId}")
    fun changeStatus(
        @PathVariable providerId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandProviderService.updateStatus(providerId, principalDetails.account.clientId!!)

        return BasicResponse.ok("")
    }

    @GetMapping("/list")
    fun providerList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
    ) = BasicResponse.ok(
        queryProviderService.adminList(principalDetails.account.clientId!!).map {
            ProviderListResponse(
                id = it.id!!,
                name = it.name!!,
                description = it.description,
                status = it.status!!.code,
                apiUrl = it.apiUrl!!,
                balance = it.balance!!
            )
        }
    )
}