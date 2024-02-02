package com.hwalaon.wezxro_server.global.security.principal

import com.hwalaon.wezxro_server.domain.account.model.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails (
    val account: Account
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = ArrayList()

    override fun getPassword(): String? = account.password

    override fun getUsername(): String? = account.name

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}