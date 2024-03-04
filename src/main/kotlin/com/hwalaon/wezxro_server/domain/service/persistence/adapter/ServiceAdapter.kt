package com.hwalaon.wezxro_server.domain.service.persistence.adapter

import com.hwalaon.wezxro_server.domain.order.persistence.port.ServicePort
import com.hwalaon.wezxro_server.domain.order.persistence.port.dto.ServiceAddOrderInfoDto
import com.hwalaon.wezxro_server.domain.service.model.constant.ServiceType
import com.hwalaon.wezxro_server.domain.service.persistence.customRepository.CustomServiceRepository
import org.springframework.stereotype.Component

@Component
class ServiceAdapter(
    private val serviceRepository: CustomServiceRepository
): ServicePort {
    override fun serviceAddOrderInfo(serviceId: Long): ServiceAddOrderInfoDto {
          serviceRepository.addOrderServiceInfo(serviceId)
        return ServiceAddOrderInfoDto(0, 0, ServiceType.COMMENTS_REPLIES, 1L)
    }
}