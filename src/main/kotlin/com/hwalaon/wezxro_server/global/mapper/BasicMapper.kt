package com.hwalaon.wezxro_server.global.mapper

interface BasicMapper<D, E> {

    fun toDomain(entity: E): D
    fun toEntity(domain: D): E
}