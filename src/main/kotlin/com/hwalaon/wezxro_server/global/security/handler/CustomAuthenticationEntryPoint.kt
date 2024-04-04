package com.hwalaon.wezxro_server.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.exception.UnAuthorizedException
import com.hwalaon.wezxro_server.global.security.exception.SecurityUnAuthorizedException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) = throw SecurityUnAuthorizedException(code = ErrorCode.UNAUTHORIZED_ERROR, request!!, response!!)
}
