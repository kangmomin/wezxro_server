package com.hwalaon.wezxro_server.global.security.principal

import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.domain.account.persistence.mapper.AccountMapper
import com.hwalaon.wezxro_server.domain.account.persistence.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class PrincipalDetailsService(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper,
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails =
        if (!username.isNullOrBlank()) PrincipalDetails(
            accountRepository.findOneByEmail(username).let {
                accountMapper.toDomain(it)
            }
        )
        // TODO: UserNotFoundException 생성하여 대체하기
        else throw UsernameNotFoundException("")
}