package com.hwalaon.wezxro_server.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.exception.TokenExpiredException
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Component
class ExceptionFilter(
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            exceptionToResponse(e.errorCode, response)
        } catch (e: ExpiredJwtException) {
            resolver.resolveException(request, response, null, TokenExpiredException())
        } catch (e: Exception) {
            e.printStackTrace()
            resolver.resolveException(request, response, null, e)
        }
    }

    private fun exceptionToResponse(errorCode: ErrorCode, response: HttpServletResponse) {
        val baseResponse = BasicResponse.BaseResponse(
            data = BasicResponse.BaseErrorResponse(errorCode.msg, errorCode.code),
            status = BasicResponse.BaseStatus.ERROR
        )

        // 상태 코드 설정
        response.status = errorCode.status.value()
        response.contentType = "application/json;charset=UTF-8"

        // 본문 설정
        response.writer.write(ObjectMapper().writeValueAsString(baseResponse))
    }
}