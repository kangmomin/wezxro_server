package com.hwalaon.wezxro_server.domain.service.persistence.adapter

import com.hwalaon.wezxro_server.domain.account.persistence.port.AccountServicePort
import com.hwalaon.wezxro_server.domain.account.persistence.port.dto.ServiceRateInfoDto
import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.provider.persistence.port.ProviderServicePort
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import com.hwalaon.wezxro_server.domain.service.persistence.repository.ServiceRepository
import com.hwalaon.wezxro_server.global.common.basic.constant.BasicStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceAdapter(
    private val customServiceRepository: CustomServiceRepository,
    private val serviceRepository: ServiceRepository
): ServicePort, ProviderServicePort, AccountServicePort {
    override fun serviceAddOrderInfo(serviceId: Long): ServiceAddOrderInfoDto {
          return customServiceRepository.addOrderServiceInfo(serviceId) ?: throw ServiceNotFoundException()
    }

    override fun deleteByProviderId(providerId: Long, clientId: UUID) {
        serviceRepository.findAllByClientIdAndProviderId(clientId, providerId).forEach { s ->
            s.status = BasicStatus.DELETED
            s.name = "deleted_service_${s.name}"
        }
    }

    override fun serviceInfo(serviceIds: List<Long>): MutableList<ServiceRateInfoDto> =
        customServiceRepository.serviceInfos(serviceIds)
}