package com.hwalaon.wezxro_server.domain.provider.controller

import com.hwalaon.wezxro_server.domain.provider.controller.request.AddProviderRequest
import com.hwalaon.wezxro_server.domain.provider.controller.request.UpdateProviderRequest
import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderDetailResponse
import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderListResponse
import com.hwalaon.wezxro_server.domain.provider.controller.response.ProviderServiceListResponse
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

    @GetMapping("/detail/{providerId}")
    fun providerDetail(
        @PathVariable providerId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val provider = queryProviderService.providerDetail(providerId, principalDetails.account.clientId!!)
        val response = ProviderDetailResponse(
            providerId = provider.id!!,
            name = provider.name!!,
            status = provider.status!!,
            apiUrl = provider.apiUrl!!,
            apiKey = provider.apiKey!!,
            balance = provider.balance!!,
            description = provider.description!!
        )

        return BasicResponse.ok(response)
    }

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

    @GetMapping("/category/{providerId}")
    fun providerServiceCategory(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable providerId: Long
    ) = BasicResponse.ok(
        queryProviderService.providerServiceCategory(providerId, principalDetails.account.clientId!!)
    )

    @PostMapping("/sync/balance/{providerId}")
    fun providerBalanceSync(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable providerId: Long
    ) = commandProviderService.syncBalance(providerId, principalDetails.account.clientId!!).let {
        BasicResponse.ok("도매처 보유액을 동기화하였습니다.")
    }

    @PostMapping("/sync/{providerId}")
    fun providerSync(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable providerId: Long
    ) = commandProviderService.syncProviderServices(providerId, principalDetails.account.clientId!!).let {
        BasicResponse.ok("도매처 서비스를 동기화하였습니다.")
    }

    @GetMapping("/services/{providerId}")
    fun providerService(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable providerId: Long,
        @RequestParam(name = "category") category: String?
    ) = BasicResponse.ok(
        queryProviderService.providerService(principalDetails.account.clientId!!, providerId, category).map {
            ProviderServiceListResponse(
                providerLink = it.providerLink!!,
                service = it.service!!,
                name = it.name!!,
                type = it.type!!,
                rate = it.rate!!,
                min = it.min!!,
                max = it.max!!,
                dripfeed = it.dripfeed!!,
                refill = it.refill!!,
                cancel = it.cancel!!,
                category = it.category!!
            )
        }
    )

    @GetMapping("/service")
    fun searchProviderService(
        @RequestParam(required = true, name = "serviceId") serviceId: String,
        @RequestParam(required = true, name = "providerId") providerId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) = BasicResponse.ok(
            queryProviderService.searchProviderService(serviceId, providerId, principalDetails.account.clientId!!)
    )

    @PatchMapping("/update")
    fun updateProvider(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateProviderRequest: UpdateProviderRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val providerRequest = updateProviderRequest.toDomain()
        providerRequest.clientId = principalDetails.account.clientId
        commandProviderService.updateProvider(providerRequest)

        return BasicResponse.ok("도매처 정보를 업데이트 하였습니다.")
    }

    @DeleteMapping("/delete/{providerId}")
    fun deleteProvider(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable providerId: Long
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandProviderService.delete(providerId, principalDetails.account.clientId!!)

        return BasicResponse.ok("도매처를 삭제하였습니다.")
    }
}