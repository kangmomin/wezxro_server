package com.hwalaon.wezxro_server.domain.service.model.constant

enum class ServiceType(str: String) {
    DEFAULT("Default"),
    SUBSCRIPTION("Subscriptions"),
    CUSTOM_COMMENTS("Custom Comments"),
    CUSTOM_COMMENTS_PACKAGE("Custom Comments"),
    MENTIONS_WITH_HASHTAGS("Mentions with hashtags"),
    MENTIONS_CUSTOM_LIST("Mentions custom list"),
    MENTIONS_HASHTAG("Mention hashtag"),
    MENTIONS_USER_FOLLOWERS("Mentions user followers"),
    MENTIONS_MEDIEA_LIKERS("Mentions media likers"),
    PACKAGE("Package"),
    COMMENT_LIKES("Commment likes"),
    POLL("Poll"),
    COMMENTS_REPLIES("Comments replies"),
    INVITES_FROM_GROUPS("Invites from groups"),
}
