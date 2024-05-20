package com.hwalaon.wezxro_server.global.common.util.request

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
class AddOrderInfoRequestDto(
    var key: String?,
    var action: String?,

    @JsonProperty("service")
    val service: String?,

    val link: String?,
    @JsonProperty("quantity")
    val quantity: Long?,
    val comments: String?,
    val usernames: String?,
    val hashtag: String?,
    val hashtags: String?,
    val username: String?,

    @JsonProperty("media")
    val media: String?,

    val min: Long?,
    val max: Long?,
    val posts: Long?,
    val delay: Long?,
    val expiry: String?
) {
    constructor(
        apiServiceId: String?,
        link: String?,
        count: Long?,
        comments: String?,
        usernames: String?,
        hashtag: String?,
        hashtags: String?,
        username: String?,
        mediaUrl: String?,
        min: Long?,
        max: Long?,
        posts: Long?,
        delay: Long?,
        expiry: String?
    ) : this(
        key = null,
        action = null,
        service = apiServiceId,
        link = link,
        quantity = count,
        comments = comments,
        usernames = usernames,
        hashtag = hashtags,
        hashtags = hashtags,
        username = username,
        media = mediaUrl,
        min = min,
        max = max,
        posts = posts,
        delay = delay,
        expiry = expiry,
    )
}
