package com.hwalaon.wezxro_server.domain.service.persistence.customRepository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QCustomRateEntity.customRateEntity
import com.hwalaon.wezxro_server.domain.service.persistence.dto.QServiceDetailAndCustomRateDto
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class CustomServiceRepository(
    private val query: JPAQueryFactory
) {

    fun serviceDetail(userId: Int, serviceId: Long) =
        query.select(QServiceDetailAndCustomRateDto(
            serviceEntity.name,
            serviceEntity.rate,
            serviceEntity.min,
            serviceEntity.max,
            serviceEntity.description,
            customRateEntity.rate,
        )).from(serviceEntity)
            .leftJoin(customRateEntity)
            .on(customRateEntity.user.userId.eq(userId).and(
                customRateEntity.serviceId.eq(serviceId)
            ))
            .where(serviceEntity.id.eq(serviceId))
            .fetchOne()

    fun validService(apiServiceId: Int, providerId: Int, serviceName: String) =
        query.select(serviceEntity.count())
            .from(serviceEntity)
            .where(
                serviceEntity.apiServiceId.eq(apiServiceId).and(
                    serviceEntity.providerId.eq(providerId)).or(
                        serviceEntity.name.eq(serviceName)
                    ))
            .fetchOne()

}