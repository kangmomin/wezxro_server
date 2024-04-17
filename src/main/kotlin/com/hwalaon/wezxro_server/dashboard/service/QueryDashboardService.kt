package com.hwalaon.wezxro_server.dashboard.service

import com.hwalaon.wezxro_server.dashboard.persistence.DashboardPersistence
import com.hwalaon.wezxro_server.global.annotation.ReadOnlyService

@ReadOnlyService
class QueryDashboardService(
    private val dashboardPersistence: DashboardPersistence
) {


}
