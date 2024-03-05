package com.hwalaon.wezxro_server.domain.order.persistence.entity

import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "\"order\"")
class OrderEntity (
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(
        nullable = false,
        updatable = false,)
    var serviceId: Long?,

    // Provider가 부여한 주문 id
    @Column(
        nullable = false,
        updatable = false,)
    var apiOrderId: Long?,

    @Column(
        nullable = false,
        updatable = false)
    var userId: Long?,

    // 토탈 금액
    @Column(nullable = false)
    var totalCharge: Double?,

    // 총 주문 양
    @Column(nullable = false)
    var count: Long?,

    @Column(columnDefinition = "TEXT")
    var comments: String?,
    @Column(columnDefinition = "TEXT")
    var commentsCustomPackage: String?,
    /** 구분자 \r\n or \n */
    @Column(columnDefinition = "TEXT")
    var hashtags: String?,
    @Column(columnDefinition = "TEXT")
    var hashtag: String?,
    /** 좋아요 누른 사람을 스크랩 해올 미디어의 URL */
    @Column(columnDefinition = "TEXT")
    var mediaUrl: String?,
    /**
     * 멘션할 유저의 닉네임. 구분자 \r\n or \n
     *  팔로워일 시 스크랩할 팔로워의 프로필 링크
     *  댓글 좋아요 일 시 타겟 댓글 작성자 이름
     *  대댓글일 시 작성자 이름
     *
     */
    @Column(columnDefinition = "TEXT")
    var usernames: String?,
    @Column(columnDefinition = "TEXT")
    var username: String?,
    /** 투표 정답 번호? */
    var answerNumber: Long?,
    @Column(columnDefinition = "TEXT")
    var groups: String?,

    // 주문 들어갈 타겟 링크
    var link: String?,

    // 남은 주문 갯수
    @Column(nullable = false, )
    var remain: Long?,

    // 완료된 주문 갯수
    @Column(nullable = false)
    var startCnt: Long?,

    // 주문 상태
    @Column(nullable = false,)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus?,

    @Column(
        nullable = false,
        updatable = false)
    @Enumerated(EnumType.STRING)
    var type: ServiceType?
): BasicTimeEntity()