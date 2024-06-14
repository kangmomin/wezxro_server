package com.hwalaon.wezxro_server.domain.wapi.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.RequestParam

class WapiAddOrderRequest (
    @RequestParam(required = false) val service: Long?,
    @RequestParam(required = false) val link: String?,
    @RequestParam(required = false) val quantity: Long?,
    @RequestParam(required = false) var comments: String?,
    @RequestParam(required = false) var commentsCustomPackage: String?,
    @RequestParam(required = false) var hashtags: String?,
    @RequestParam(required = false) var hashtag: String?,
    @RequestParam(required = false) var subDelay: Long?,
    @RequestParam(required = false) var subPosts: Long?,
    @RequestParam(required = false) var subMin: Long?,
    @RequestParam(required = false) var subMax: Long?,
    @RequestParam(required = false) var mediaUrl: String?,
    @RequestParam(required = false) var usernames: String?,
    @RequestParam(required = false) var username: String?,
    @RequestParam(required = false) var answerNumber: Long?,
    @RequestParam(required = false) var groups: String?,
    @RequestParam(required = false) var expiry: String?,
    @RequestParam(required = false) var subUsername: String?,
    @RequestParam(required = false) var usernamesCustom: String?,
)