package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.domain.account.exception.AccountNotFoundException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtParser: JwtParser
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = jwtParser.parseAccessToken(request)
            if (!accessToken.isNullOrBlank()) {
                val authentication = jwtParser.authentication(accessToken)
                SecurityContextHolder.clearContext()
                val securityContext = SecurityContextHolder.getContext()
                securityContext.authentication = authentication
            }

            filterChain.doFilter(request, response)
        } catch (e: AccountNotFoundException) {
            response.sendError(e.code.code.value(), e.code.msg)
        }
    }
}