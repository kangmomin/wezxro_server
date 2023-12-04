package com.hwalaon.wezxro_server.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.hwalaon.wezxro_server.global.BasicResponse
import com.hwalaon.wezxro_server.global.exception.BasicException
import com.hwalaon.wezxro_server.global.exception.ErrorCode
import com.hwalaon.wezxro_server.global.security.exception.ForbiddenException
import com.hwalaon.wezxro_server.global.security.exception.UnAuthorizedException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class ExceptionFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure {
            when(it) {
                is UnAuthorizedException -> exceptionToResponse(ErrorCode.UNAUTHORIZED_ERROR, response)
                is ForbiddenException -> exceptionToResponse(ErrorCode.FORBIDDEN_ERROR, response)
            }
        }
    }

    private fun exceptionToResponse(errorCode: ErrorCode, response: HttpServletResponse) {
        response.status = errorCode.code.value()
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        val errorResponse = BasicResponse.error(errorCode)
        val errorResponseToJson = ObjectMapper().writeValueAsString(errorResponse)
        response.writer.write(errorResponseToJson)
    }
}