package com.hwalaon.wezxro_server.domain.service.mapper

import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.entity.ServiceEntity
import com.hwalaon.wezxro_server.global.mapper.BasicMapper

class ServiceMapper: BasicMapper<Service, ServiceEntity> {
    override fun toDomain(entity: ServiceEntity): Service =
        Service(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            categoryId = entity.categoryId,
            apiServiceId = entity.apiServiceId,
            providerId = entity.providerId,
            type = entity.type,
            max = entity.max,
            min = entity.min,
            originalRate = entity.originalRate,
            rate = entity.rate,
            status = entity.status,
        ).let {
            it.createdAt = entity.createdAt
            it.updatedAt = entity.updatedAt
            return it
        }

    override fun toEntity(domain: Service): ServiceEntity =
        ServiceEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            categoryId = domain.categoryId,
            apiServiceId = domain.apiServiceId,
            providerId = domain.providerId,
            type = domain.type,
            max = domain.max,
            min = domain.min,
            originalRate = domain.originalRate,
            rate = domain.rate,
            status = domain.status,
        ).let {
            it.createdAt = domain.createdAt
            it.updatedAt = domain.updatedAt
            return it
        }
}