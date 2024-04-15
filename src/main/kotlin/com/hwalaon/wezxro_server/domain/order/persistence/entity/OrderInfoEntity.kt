package com.hwalaon.wezxro_server.domain.order.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_info")
class OrderInfoEntity (

    @Id
    @Column(name = "orderInfoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    var order: OrderEntity?,
    @Column( columnDefinition = "TEXT" )
    var link: String?,
    @Column( columnDefinition = "TEXT" )
    var comments: String?,
    @Column( columnDefinition = "TEXT" )
    var commentsCustomPackage: String?,
    @Column( columnDefinition = "TEXT" )
    var usernames: String?,
    @Column( columnDefinition = "TEXT" )
    var usernamesCustom: String?,
    @Column( columnDefinition = "TEXT" )
    var hashtags: String?,
    @Column( columnDefinition = "TEXT" )
    var hashtag: String?,
    @Column( columnDefinition = "TEXT" )
    var username: String?,
    @Column( columnDefinition = "TEXT" )
    var mediaUrl: String?,
    @Column( columnDefinition = "TEXT" )
    var subUsername: String?,
    var subPosts: Long?,
    var subMin: Long?,
    var subMax: Long?,
    var subDelay: Long?,
    var expiry: String?,
)