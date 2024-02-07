package com.hwalaon.wezxro_server.domain.service.controller

import com.hwalaon.wezxro_server.domain.service.controller.request.AddServiceRequest
import com.hwalaon.wezxro_server.domain.service.service.admin.CommandAdminServiceService
import com.hwalaon.wezxro_server.domain.service.service.admin.QueryAdminServiceService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.basic.response.MsgResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
        @RequestBody addServiceRequest: AddServiceRequest
    ) =
        commandAdminServiceService.add(addServiceRequest.toDomain()).let {
            BasicResponse.created(MsgResponse(""))
        }

    @DeleteMapping("/delete/{id}")
    fun deleteService(
        @RequestParam("id") id: Int
    ) = commandAdminServiceService.delete(id).run {
        BasicResponse.ok("서비스를 삭제하였습니다.")
    }
}