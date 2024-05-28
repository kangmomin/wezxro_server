package com.hwalaon.wezxro_server.domain.order.persistence.adapter

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.persistence.port.OrderPort
import com.hwalaon.wezxro_server.domain.order.model.constant.OrderStatus
import com.hwalaon.wezxro_server.domain.order.persistence.customRepository.CustomOrderRepository
import com.hwalaon.wezxro_server.domain.order.persistence.port.ProviderPort
import com.hwalaon.wezxro_server.domain.provider.exception.ProviderNotFoundException
import com.hwalaon.wezxro_server.global.common.util.ApiProvider
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDashboardAdapter(
    private val customOrderRepository: CustomOrderRepository,
    private val providerPort: ProviderPort
): OrderPort {

    override fun dashboardByUserId(userId: Long): MutableList<DashboardResponse.OrderStatusCntDto>? =
        customOrderRepository.dashboardByUserId(userId)

    override fun dashboardByClientId(clientId: UUID): MutableList<DashboardResponse.OrderStatusCntDto>? =
        customOrderRepository.dashboardByClientId(clientId)

    override fun updateOrderStatus(userId: Long): String? {
        val orderInfos = customOrderRepository.orderInfoByUserId(userId)
        val providerIds = orderInfos.map { it.providerId!! }.distinct()
        val providers = providerPort.getProvidersById(providerIds)

        providerIds.forEach {providerId ->
            val orderIds = orderInfos.mapNotNull {
                if (it.providerId == providerId) it.apiOrderId
                else null
            }

            val p = providers.find { it.id == providerId } ?: throw ProviderNotFoundException()

            val multipleOrdersStatus = ApiProvider(p.apiKey!!, p.apiUrl!!).getMultipleOrdersStatus(orderIds)

            orderIds.forEach {orderId ->
                val newOrder = multipleOrdersStatus[orderId.toString()]!!
                if (newOrder.error == null) {
                    val order = orderInfos.find {
                        it.apiOrderId == orderId && it.providerId == providerId
                    }

                    order!!.status = OrderStatus.valueOf(
                        newOrder.status!!.replace(" ", "")
                            .uppercase(Locale.getDefault()))
                    order.remain = newOrder.remains ?: 0
                    order.startCnt = newOrder.start_count ?: 0
                }
            }
        }

        return "success"
    }
}