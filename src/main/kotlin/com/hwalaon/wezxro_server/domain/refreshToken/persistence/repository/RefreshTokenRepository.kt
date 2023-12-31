package com.hwalaon.wezxro_server.domain.refreshToken.persistence.repository

import com.hwalaon.wezxro_server.domain.refreshToken.model.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {

    fun existsByRefreshToken(refreshToken: String): Boolean
}