package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.controller.request.SaveCategoryRequest
import com.hwalaon.wezxro_server.domain.category.service.CommandCategoryService
import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RequestMapping("/c")
@RestController
class CategoryController(
    private val queryCategoryService: QueryCategoryService,
    private val commandCategoryService: CommandCategoryService
) {

    @GetMapping("/list")
    fun categoryList() =
        queryCategoryService.categoryList()

    @PostMapping("/add")
    fun categoryAdd(@RequestBody @Valid categoryRequest: SaveCategoryRequest) =
        commandCategoryService.addCategory(categoryRequest.toDomain())

    @PostMapping("/delete/{id}")
    fun categoryDelete(@PathVariable("id") id: Long) =
        commandCategoryService.delete(id)

    @PostMapping("/update/{id}")
    fun categoryUpdate(@RequestBody @Valid categoryRequest: SaveCategoryRequest,
                       @PathVariable("id") categoryId: Long) =
        commandCategoryService.updateCategory(categoryId, categoryRequest.toDomain())


}