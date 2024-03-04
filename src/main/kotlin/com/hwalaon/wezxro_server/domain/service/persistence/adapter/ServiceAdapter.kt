package com.hwalaon.wezxro_server.domain.service.persistence.adapter

import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import org.springframework.stereotype.Component

@Component
class ServiceAdapter(
    private val serviceRepository: CustomServiceRepository
): ServicePort {
    override fun serviceAddOrderInfo(serviceId: Long): ServiceAddOrderInfoDto {
          return serviceRepository.addOrderServiceInfo(serviceId) ?: throw ServiceNotFoundException()
    }
}