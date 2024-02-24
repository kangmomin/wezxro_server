package com.hwalaon.wezxro_server.domain.service.persistence.customRepository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QCustomRateEntity.customRateEntity
import com.hwalaon.wezxro_server.domain.service.persistence.dto.QServiceDetailAndCustomRateDto
import com.hwalaon.wezxro_server.domain.service.persistence.dto.ServiceDetailAndCustomRateDto
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomServiceRepository(
    private val query: JPAQueryFactory
) {

    fun serviceDetail(userId: Int, serviceId: Long, clientId: UUID, isAdmin: Boolean = false) =
        query.select(QServiceDetailAndCustomRateDto(
            serviceEntity.id,
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
            .where(
                if (isAdmin) serviceEntity.status.ne(BasicStatus.DELETED)
                else serviceEntity.status.eq(BasicStatus.ACTIVE),
                serviceEntity.id.eq(serviceId)
                    .and(serviceEntity.clientId.eq(clientId)))
            .fetchOne()

    fun validService(apiServiceId: Int, providerId: Int, serviceName: String) =
        query.select(serviceEntity.count())
            .from(serviceEntity)
            .where(
                serviceEntity.status.ne(BasicStatus.DELETED),
                serviceEntity.apiServiceId.eq(apiServiceId).and(
                    serviceEntity.providerId.eq(providerId)).or(
                        serviceEntity.name.eq(serviceName)
                    ))
            .fetchOne()

    fun serviceDetailList(userId: Int?, clientId: UUID?, isAdmin: Boolean = false): MutableList<ServiceDetailAndCustomRateDto> =
        query.select(QServiceDetailAndCustomRateDto(
            serviceEntity.id,
            serviceEntity.name,
            serviceEntity.rate,
            serviceEntity.min,
            serviceEntity.max,
            serviceEntity.description,
            customRateEntity.rate,
        )).from(serviceEntity)
            .leftJoin(customRateEntity)
            .on(customRateEntity.user.userId.eq(userId).and(
                customRateEntity.serviceId.eq(serviceEntity.id)
            ))
            .where(
                if (isAdmin) serviceEntity.status.ne(BasicStatus.DELETED)
                else serviceEntity.status.eq(BasicStatus.ACTIVE),
                serviceEntity.clientId.eq(clientId))
            .fetch()

}