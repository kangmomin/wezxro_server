package com.hwalaon.wezxro_server.domain.category.persistence.entity

import com.hwalaon.wezxro_server.global.constant.BasicStatus
import jakarta.persistence.*

@Table(name="category")
class CategoryEntity (
    @Id @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(unique = true)
    var name: String?,

    var sort: Int?,

    @Enumerated(EnumType.STRING)
    var status: BasicStatus?
)