package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.controller.request.StoreServiceRequest
import com.hwalaon.wezxro_server.domain.service.service.admin.CommandAdminServiceService
import com.hwalaon.wezxro_server.domain.service.service.admin.QueryAdminServiceService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.basic.response.MsgResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/s")
class ServiceAdminController(
    private val commandAdminServiceService: CommandAdminServiceService,
    private val queryAdminServiceService: QueryAdminServiceService
) {

    @GetMapping("/list")
    fun getServices(
        @AuthenticationPrincipal userInfo: PrincipalDetails,
        @RequestParam(name = "category", defaultValue = "0") categoryId: Long
    ) =
        queryAdminServiceService.serviceList(userInfo.account.clientId, categoryId).let {
            BasicResponse.ok(it)
        }

    @PostMapping("/add")
    fun addService(
        @RequestBody @Valid storeServiceRequest: StoreServiceRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        commandAdminServiceService.add(
            storeServiceRequest.toDomain(principalDetails.account.clientId!!)
        ).let {
            BasicResponse.created(MsgResponse("서비스를 생성하였습니다."))
        }

    @DeleteMapping("/delete/{id}")
    fun deleteService(
        @PathVariable id: Int
    ) = commandAdminServiceService.delete(id).run {
        BasicResponse.ok("서비스를 삭제하였습니다.")
    }

    @PatchMapping("/status/{serviceId}")
    fun changeStatus(
        @PathVariable serviceId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val updatedStatus = commandAdminServiceService.updateStatus(serviceId, principalDetails.account.clientId!!)

        return BasicResponse.ok("서비스를 ${updatedStatus.name}로 수정하였습니다.")
    }

    @PatchMapping("/update/{serviceId}")
    fun updateService(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid storeServiceRequest: StoreServiceRequest,
        @PathVariable serviceId: Long
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAdminServiceService.update(storeServiceRequest, serviceId, principalDetails.account.clientId!!)

        return BasicResponse.ok("서비스를 업데이트 하였습니다.")
    }

    @DeleteMapping("/custom-rate/{serviceId}")
    fun deleteServiceCustomRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable serviceId: Long
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAdminServiceService.deleteCustomRate(serviceId)

        return BasicResponse.ok("서비스의 개별 감가액을 전부 삭제하였습니다.")
    }
}