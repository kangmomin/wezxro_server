package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.controller.request.CategorySortRequest
import com.hwalaon.wezxro_server.domain.category.controller.request.SaveCategoryRequest
import com.hwalaon.wezxro_server.domain.category.service.CommandCategoryService
import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
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

    @PatchMapping("/update/{id}")
    fun categoryUpdate(@RequestBody @Valid categoryRequest: SaveCategoryRequest,
                       @PathVariable("id") categoryId: Long): ResponseEntity<BasicResponse.BaseResponse> {
        commandCategoryService.updateCategory(categoryId, categoryRequest.toDomain())
        return BasicResponse.ok("서비스를 수정하였습니다.")
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

    @PatchMapping("/status/{id}")
    fun categoryStatusToggle(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val status = commandCategoryService.toggleStatus(categoryId, principalDetails.account.clientId!!)
        return BasicResponse.ok("카테고리를 ${if (status == BasicStatus.ACTIVE) "활성화" else "비활성화"} 했습니다.")
    }

    @PatchMapping("/sort/{id}")
    fun categorySortUpdate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
        @RequestBody @Valid categorySortRequest: CategorySortRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandCategoryService.updateSort(categoryId, categorySortRequest.sort!!, principalDetails.account.clientId!!)
        return BasicResponse.ok("정렬 번호를 수정하였습니다.")
    }
}