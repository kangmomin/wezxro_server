package com.hwalaon.wezxro_server.dashboard.service

import com.hwalaon.wezxro_server.dashboard.controller.response.DashboardResponse
import com.hwalaon.wezxro_server.dashboard.persistence.DashboardPersistence
import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.global.annotation.CommandService
import java.util.*
import kotlin.collections.ArrayList

@CommandService
class CommandDashboardService(
    private val dashboardPersistence: DashboardPersistence
) {
    fun userDashboard(userId: Long): DashboardResponse {
        dashboardPersistence.updateOrderStatus(userId)
        val orderDataByUserId = dashboardPersistence.getOrderDataByUserId(userId) ?: ArrayList()
        val accountPayInfoDto = dashboardPersistence.getPayInfo(userId) ?: throw AccountNotFoundException()

        var totalUsed = 0.0

        orderDataByUserId.forEach {
            totalUsed += it.charge
        }

        return DashboardResponse(
            orderStatusCnt = orderDataByUserId.toMutableList(),
            balance = accountPayInfoDto.balance,
            totalUsed = totalUsed,
            totalOrder = orderDataByUserId.count().toLong()
        )
    }

    fun adminDashboard(clientId: UUID): DashboardResponse {
        val orderDataByUserId = dashboardPersistence.getOrderDataByClientId(clientId) ?: ArrayList()
        val accountPayInfoDto = dashboardPersistence.getPayInfo(clientId)?.balance ?: 0.0

        var totalUsed = 0.0

        orderDataByUserId.forEach {
            totalUsed += it.charge
        }

        return DashboardResponse(
            orderStatusCnt = orderDataByUserId.toMutableList(),
            balance = accountPayInfoDto,
            totalUsed = totalUsed,
            totalOrder = orderDataByUserId.count().toLong()
        )
    }
}