package com.hwalaon.wezxro_server.domain.service.service.user

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService
import javax.management.ServiceNotFoundException

@ReadOnlyService
class QueryUserServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
    fun serviceDetail(serviceId: Long, user: Account) =
        servicePersistenceAdapter.userServiceDetail(serviceId, user.userId!!).let {
            if (it == null) throw ServiceNotFoundException()

            // 감가액 적용
            var rate = (if (it.customRate != null) it.rate / it.customRate
                        else it.rate.times(user.staticRate ?: 0F)) * 100F
            rate = Math.round(rate) / 100F


            Service(
                id = serviceId,
                rate = rate,
                name = it.name,
                description = it.description,
                max = it.max,
                min = it.min,
                status = null,
                type = null,
                originalRate = null,
                clientId = null,
                categoryId = null,
                providerId = null,
                apiServiceId = null,
            )
        }

}