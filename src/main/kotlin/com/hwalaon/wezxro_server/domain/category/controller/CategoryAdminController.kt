package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.controller.request.CategorySortRequest
import com.hwalaon.wezxro_server.domain.category.controller.request.SaveCategoryRequest
import com.hwalaon.wezxro_server.domain.category.service.CommandCategoryService
import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/c")
class CategoryAdminController(
    private val queryCategoryService: QueryCategoryService,
    private val commandCategoryService: CommandCategoryService
) {

    @PostMapping("/add")
    fun categoryAdd(
        @RequestBody @Valid categoryRequest: SaveCategoryRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            commandCategoryService.addCategory(
                categoryRequest.toDomain(), principalDetails.account.clientId!!))

    @PostMapping("/delete/{id}")
    fun categoryDelete(@PathVariable("id") id: Long) =
        commandCategoryService.delete(id).run {
            BasicResponse.ok("서비스를 삭제하였습니다.")
        }

    @PostMapping("/update/{id}")
    fun categoryUpdate(@RequestBody @Valid categoryRequest: SaveCategoryRequest,
                       @PathVariable("id") categoryId: Long) =
        commandCategoryService.updateCategory(
            categoryId,
            categoryRequest.toDomain()).run {
            BasicResponse.ok("서비스를 수정하였습니다.")
        }

    @GetMapping("/detail/{categoryId}")
    fun categoryDetail(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable categoryId: Long,
    ) =
        BasicResponse.ok(
            queryCategoryService.detail(
                principalDetails.account.clientId,
                categoryId, true))

    @GetMapping("/list")
    fun categoryList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            queryCategoryService.categoryListForAdmin(principalDetails.account.clientId!!)
        )

    @PostMapping("/status/{id}")
    fun categoryStatusToggle(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
    ) = BasicResponse.ok(
        commandCategoryService.toggleStatus(categoryId, principalDetails.account.clientId!!)
    )

    @PostMapping("/sort/{id}")
    fun categorySortUpdate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
        @RequestBody @Valid categorySortRequest: CategorySortRequest
    ) = BasicResponse.ok(
        commandCategoryService.updateSort(categoryId, categorySortRequest.sort!!, principalDetails.account.clientId!!)
    )
}