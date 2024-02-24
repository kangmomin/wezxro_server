package com.hwalaon.wezxro_server.global.security.principal

import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountEntityRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class PrincipalDetailsService(
    private val accountEntityRepository: AccountEntityRepository,
    private val accountMapper: AccountMapper,
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        if (!username.isNullOrBlank()) PrincipalDetails(
            accountEntityRepository.findAllByUserIdAndStatusNot(username.toInt()).let {
                if (it == null) throw AccountNotFoundException()
                accountMapper.toDomain(it)
            }
        )

        else throw AccountNotFoundException()
}