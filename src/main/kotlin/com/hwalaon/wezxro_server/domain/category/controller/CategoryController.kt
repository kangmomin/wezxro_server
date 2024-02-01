package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.controller.request.SaveCategoryRequest
import com.hwalaon.wezxro_server.domain.category.service.CommandCategoryService
import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/c")
@RestController
class CategoryController(
    private val queryCategoryService: QueryCategoryService,
    private val commandCategoryService: CommandCategoryService
) {

    @GetMapping("/list/{clientId}")
    fun categoryList(@PathVariable clientId: UUID) =
        queryCategoryService.categoryList(clientId)

    @PostMapping("/add")
    fun categoryAdd(@RequestBody @Valid categoryRequest: SaveCategoryRequest) =
        BasicResponse.ok(
            commandCategoryService.addCategory(
                categoryRequest.toDomain()))

    @PostMapping("/delete/{id}")
    fun categoryDelete(@PathVariable("id") id: Long) =
        commandCategoryService.delete(id).run {
            BasicResponse.okMsg("서비스를 삭제하였습니다.")
        }

    @PostMapping("/update/{id}")
    fun categoryUpdate(@RequestBody @Valid categoryRequest: SaveCategoryRequest,
                       @PathVariable("id") categoryId: Long) =
        commandCategoryService.updateCategory(
            categoryId,
            categoryRequest.toDomain()).run {
                BasicResponse.okMsg("서비스를 수정하였습니다.")
        }
}