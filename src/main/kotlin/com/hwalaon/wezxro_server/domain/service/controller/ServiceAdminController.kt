package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.controller.request.AddServiceRequest
import com.hwalaon.wezxro_server.domain.service.service.admin.CommandAdminServiceService
import com.hwalaon.wezxro_server.domain.service.service.admin.QueryAdminServiceService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.basic.response.MsgResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/s/admin")
class ServiceAdminController(
    private val commandAdminServiceService: CommandAdminServiceService,
    private val queryAdminServiceService: QueryAdminServiceService
) {

    @GetMapping("/list")
    fun getServices(
        @AuthenticationPrincipal userInfo: PrincipalDetails,
        @RequestParam(name = "category", defaultValue = "0") categoryId: Int) =
        queryAdminServiceService.serviceList(userInfo.account.clientId, categoryId).let {
            BasicResponse.ok(it)
        }

    @PostMapping("/add")
    fun addService(
        @RequestBody addServiceRequest: AddServiceRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        commandAdminServiceService.add(
            addServiceRequest.toDomain(principalDetails.account.clientId!!)
        ).let {
            BasicResponse.created(MsgResponse("서비스를 생성하였습니다."))
        }

    @DeleteMapping("/delete/{id}")
    fun deleteService(
        @PathVariable id: Int
    ) = commandAdminServiceService.delete(id).run {
        BasicResponse.ok("서비스를 삭제하였습니다.")
    }
}