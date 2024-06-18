package com.hwalaon.wezxro_server.global.common.client.persistence.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "client")
class ClientEntity (
    @Id
    @Column(
        name = "client_id",
        updatable = false,
        unique = true,
        nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,

    @Column(unique = true,
        updatable = true,
        nullable = false)
    var domain: String?,

    @Column(nullable = false,)
    var email: String?,

    @Column(nullable = false,)
    var emailPassword: String?
)