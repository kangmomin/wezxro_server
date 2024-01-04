package com.hwalaon.wezxro_server.domain.category.controller

import com.hwalaon.wezxro_server.domain.category.service.QueryCategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/c")
@RestController
class CategoryController(
    private val queryCategoryService: QueryCategoryService
) {

    @GetMapping("/list")
    fun categoryList() =
        queryCategoryService.categoryList()
}