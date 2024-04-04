package com.hwalaon.wezxro_server.global.security.jwt

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.security.exception.CustomSecurityException
import com.hwalaon.wezxro_server.global.security.exception.TokenExpiredException
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetailsService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtParser: JwtParser,
    private val principalDetailsService: PrincipalDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = jwtParser.parseAccessToken(request, response)
        if (!accessToken.isNullOrBlank()) {
            try {
                val userEmail = jwtParser.authentication(accessToken, true)
                val userDetails = principalDetailsService.loadUserByUsername(userEmail)

                val securityContext = SecurityContextHolder.getContext()
                securityContext.authentication = UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
            } catch(e: ExpiredJwtException) {
                throw TokenExpiredException(req = request, res = response)
            } catch (e: BasicException) {
                // BasicException 에 response, request 데이터를 담을 수 있는 객체로 변환
                throw CustomSecurityException(response, request, e.errorCode)
            }
        }

        filterChain.doFilter(request, response)
    }
}