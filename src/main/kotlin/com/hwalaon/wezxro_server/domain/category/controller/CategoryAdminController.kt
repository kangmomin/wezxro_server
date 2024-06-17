package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.controller.request.CategorySortRequest
import com.hwalaon.wezxro_server.domain.category.controller.request.SaveCategoryRequest
import com.hwalaon.wezxro_server.domain.category.service.CommandCategoryService
import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/c")
class CategoryAdminController(
    private val queryCategoryService: QueryCategoryService,
    private val commandCategoryService: CommandCategoryService
) {

    private val logger = LoggerFactory.getLogger(CategoryAdminController::class.java)

    @PostMapping("/add")
    fun categoryAdd(
        @RequestBody @Valid categoryRequest: SaveCategoryRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val category = commandCategoryService.addCategory(
            categoryRequest.toDomain(), principalDetails.account.clientId!!
        )
        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Create: by - ${principalDetails.account.userId!!} / ${category.id}")

        return BasicResponse.ok(category)
    }

    @PostMapping("/delete/{id}")
    fun categoryDelete(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandCategoryService.delete(id)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Delete: by - ${principalDetails.account.userId!!} / $id")

        return BasicResponse.ok("카테고리를 삭제하였습니다.")
    }

    @PatchMapping("/update/{id}")
    fun categoryUpdate(
        @RequestBody @Valid categoryRequest: SaveCategoryRequest,
        @PathVariable("id") categoryId: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandCategoryService.updateCategory(categoryId, categoryRequest.toDomain())

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Delete: by - ${principalDetails.account.userId!!} / $categoryId")

        return BasicResponse.ok("카테고리를 수정하였습니다.")
    }

    @GetMapping("/detail/{categoryId}")
    fun categoryDetail(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable categoryId: Long,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val categoryDetail = queryCategoryService.detail(
            principalDetails.account.clientId,
            categoryId, true
        )

        return BasicResponse.ok(categoryDetail)
    }

    @GetMapping("/list")
    fun categoryList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) = BasicResponse.ok(
            queryCategoryService.categoryListForAdmin(principalDetails.account.clientId!!)
        )

    @PatchMapping("/status/{id}")
    fun categoryStatusToggle(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val status = commandCategoryService.toggleStatus(categoryId, principalDetails.account.clientId!!)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / $categoryId / $status")

        return BasicResponse.ok("카테고리를 ${if (status == BasicStatus.ACTIVE) "활성화" else "비활성화"} 했습니다.")
    }

    @PatchMapping("/sort/{id}")
    fun categorySortUpdate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable(name = "id") categoryId: Long,
        @RequestBody @Valid categorySortRequest: CategorySortRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandCategoryService.updateSort(categoryId, categorySortRequest.sort!!, principalDetails.account.clientId!!)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / $categoryId / ${categorySortRequest.sort}")

        return BasicResponse.ok("정렬 번호를 수정하였습니다.")
    }
}