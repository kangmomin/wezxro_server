package com.hwalaon.wezxro_server.domain.category.persistence.entity

import com.hwalaon.wezxro_server.domain.category.model.Category
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.hwalaon.wezxro_server.global.common.basic.entity.BasicTimeEntity
import jakarta.persistence.*
import java.util.*

@Table(name="category")
@Entity
class CategoryEntity (
    @Id @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(length = 200)
    var name: String?,

    var sort: Int?,

    @Enumerated(EnumType.STRING)
    var status: BasicStatus?,

    @Column(nullable = false)
    var clientId: UUID?
): BasicTimeEntity() {
    fun update(category: Category) = this.let {
        it.name = category.name
        it.sort = category.sort
        it.status = category.status
    }
}