package com.hwalaon.wezxro_server.domain.order.model

class OrderInfo(
    var id: Long?,
    var order: Order?,
    var link: String?,
    var comments: String?,
    var commentsCustomPackage: String?,
    /**
     * 멘션할 유저의 닉네임. 구분자 \r\n or \n
     *  팔로워일 시 스크랩할 팔로워의 프로필 링크
     *  댓글 좋아요 일 시 타겟 댓글 작성자 이름
     *  대댓글일 시 작성자 이름
     */
    var usernames: String?,
    var usernamesCustom: String?,
    /** 구분자 \r\n or \n */
    var hashtags: String?,
    var hashtag: String?,
    var username: String?,
    /** 좋아요 누른 사람을 스크랩 해올 미디어의 URL */
    var mediaUrl: String?,
    var subUsername: String?,
    var subPosts: Long?,
    var subMin: Long?,
    var subMax: Long?,
    var subDelay: Long?,
    var expiry: String?,
)
