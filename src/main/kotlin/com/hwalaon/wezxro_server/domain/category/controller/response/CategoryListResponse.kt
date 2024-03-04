package com.hwalaon.wezxro_server.domain.category.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryListResponse (
    @JsonProperty("categoryId")
    val id: Long,
    @JsonProperty("name")
    val name: String,
)
