package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/c")
@RestController
class CategoryController(
    private val queryCategoryService: QueryCategoryService
) {

    @GetMapping("/list")
    fun categoryList(
        @AuthenticationPrincipal userInfo: PrincipalDetails) =
        BasicResponse.ok(
            queryCategoryService.categoryList(userInfo.account.clientId!!))

    @GetMapping("/detail/{categoryId}")
    fun categoryDetail(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable categoryId: Long,
    ) =
        BasicResponse.ok(
            queryCategoryService.detail(
                principalDetails.account.clientId,
                categoryId))
}