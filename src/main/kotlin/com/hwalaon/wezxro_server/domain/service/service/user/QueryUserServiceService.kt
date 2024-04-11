package com.hwalaon.wezxro_server.domain.service.service.user

import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.service.exception.ServiceNotFoundException
import com.hwalaon.wezxro_server.domain.service.model.Service
import com.hwalaon.wezxro_server.domain.service.persistence.ServicePersistenceAdapter
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService

@ReadOnlyService
class QueryUserServiceService(
    private val servicePersistenceAdapter: ServicePersistenceAdapter
) {
        fun serviceDetail(serviceId: Long, user: Account) =
        servicePersistenceAdapter.userServiceDetail(serviceId, user.userId!!, user.clientId!!).let {
            if (it == null) throw ServiceNotFoundException()

            // 감가액 적용
            var rate = (if (it.customRate != null) it.rate / it.customRate
                        else it.rate.times(user.staticRate ?: 0.0)) * 100.0
            rate = Math.round(rate) / 100.0


            Service.serviceDetail(
                serviceId = serviceId,
                rate = rate,
                name = it.name,
                description = it.description,
                max = it.max,
                min = it.min,
                type = it.type)
        }

    fun serviceDetailList(account: Account, category: Long?) =
        servicePersistenceAdapter.userServiceDetailList(account.userId, account.clientId, category).map {
            Service.serviceDetail(
                serviceId = it.serviceId,
                name = it.name,
                max = it.max,
                min = it.min,
                rate = it.rate,
                description = it.description,
                type = it.type
            )
        }

}