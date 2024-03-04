package com.hwalaon.wezxro_server.domain.order.model

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.model.BasicTime

class Order (
    var id: Long?,
    var serviceId: Long?,
    var apiOrderId: Long?,
    var userId: Long?,
    var totalCharge: Double?,
    var count: Long?,
    var comments: List<String>?,
    var commentsCustomPackage: String?,
    /** 구분자 \r\n or \n */
    var hashtags: List<String>?,
    var hashtag: String?,
    /** 좋아요 누른 사람을 스크랩 해올 미디어의 URL */
    var mediaUrl: String?,
    /**
     * 멘션할 유저의 닉네임. 구분자 \r\n or \n
     *  팔로워일 시 스크랩할 팔로워의 프로필 링크
     *  댓글 좋아요 일 시 타겟 댓글 작성자 이름
     *  대댓글일 시 작성자 이름
     *
     */
    var usernames: List<String>?,
    var username: String?,
    /** 투표 정답 번호? */
    var answerNumber: Long?,
    var groups: String?,
    var link: String?,
    var remain: Long?,
    var startCnt: Long?,
    var status: OrderStatus?,
    var type: ServiceType?
): BasicTime()