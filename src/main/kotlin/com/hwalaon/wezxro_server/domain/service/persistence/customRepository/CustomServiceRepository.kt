package com.hwalaon.wezxro_server.domain.service.persistence.customRepository

import com.hwalaon.wezxro_server.domain.account.persistence.entity.QCustomRateEntity.customRateEntity
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.QServiceRateInfoDto
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ServiceRateInfoDto
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.QServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.service.persistence.dto.QServiceDetailAndCustomRateDto
import com.hwalaon.wezxro_server.domain.service.persistence.dto.ServiceDetailAndCustomRateDto
import com.hwalaon.wezxro_server.domain.service.persistence.entity.QServiceEntity.serviceEntity
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomServiceRepository(
    private val query: JPAQueryFactory
) {

    fun serviceDetail(userId: Long, serviceId: Long, clientId: UUID, isAdmin: Boolean = false) =
        query.select(QServiceDetailAndCustomRateDto(
            serviceEntity.id,
            serviceEntity.name,
            serviceEntity.rate,
            serviceEntity.min,
            serviceEntity.max,
            serviceEntity.description,
            customRateEntity.rate,
            serviceEntity.type
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

    fun validService(apiServiceId: Long, providerId: Long, serviceName: String, serviceId: Long?): Long? {
        val query = query.select(serviceEntity.count())
            .from(serviceEntity)
            .where(
                serviceEntity.status.ne(BasicStatus.DELETED),
                serviceEntity.apiServiceId.eq(apiServiceId).and(
                    serviceEntity.providerId.eq(providerId)
                ).or(
                    serviceEntity.name.eq(serviceName)
                )
            )

        if (serviceId != null) query.where(serviceEntity.id.ne(serviceId))

        return query.fetchOne()
    }

    fun serviceDetailList(userId: Long?, clientId: UUID?, category: Long?, isAdmin: Boolean = false): MutableList<ServiceDetailAndCustomRateDto> {
        val sql = query.select(
            QServiceDetailAndCustomRateDto(
                serviceEntity.id,
                serviceEntity.name,
                serviceEntity.rate,
                serviceEntity.min,
                serviceEntity.max,
                serviceEntity.description,
                customRateEntity.rate,
                serviceEntity.type
            )
        ).from(serviceEntity)
            .leftJoin(customRateEntity)
            .on(
                customRateEntity.user.userId.eq(userId).and(
                    customRateEntity.serviceId.eq(serviceEntity.id)
                )
            )

        if (category != null) sql.where(serviceEntity.categoryId.eq(category))

        sql.where(
            if (isAdmin) serviceEntity.status.ne(BasicStatus.DELETED)
            else serviceEntity.status.eq(BasicStatus.ACTIVE),
            serviceEntity.clientId.eq(clientId))
        return sql.fetch()
    }

    fun addOrderServiceInfo(serviceId: Long): ServiceAddOrderInfoDto? {
        return query.select(QServiceAddOrderInfoDto(
            serviceEntity.min,
            serviceEntity.max,
            serviceEntity.type,
            serviceEntity.apiServiceId,
            serviceEntity.providerId
        ))
            .from(serviceEntity)
            .where(serviceEntity.id.eq(serviceId))
            .fetchOne()
    }

    fun serviceInfos(serviceIds: List<Long>): MutableList<ServiceRateInfoDto> =
        query.select(QServiceRateInfoDto(
            serviceEntity.id,
            serviceEntity.name,
            serviceEntity.rate
        ))
            .from(serviceEntity)
            .where(serviceEntity.id.`in`(serviceIds))
            .orderBy(serviceEntity.id.desc())
            .fetch()

}