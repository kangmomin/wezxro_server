package com.hwalaon.wezxro_server.domain.provider.persistence.customRepository

import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.QProviderApiDto
import com.hwalaon.wezxro_server.domain.provider.persistence.entity.QProviderEntity.providerEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomProviderRepository(
    private val query: JPAQueryFactory
) {
    fun getApiInfo(providerId: Long) =
        query.select(QProviderApiDto(
            providerEntity.apiKey,
            providerEntity.apiUrl
        ))
            .from(providerEntity)
            .where(providerEntity.id.eq(providerId))
            .fetchOne()
}
