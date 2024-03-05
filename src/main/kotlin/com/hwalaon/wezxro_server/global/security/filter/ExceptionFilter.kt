package com.hwalaon.wezxro_server.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import io.jsonwebtoken.ExpiredJwtException
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
        try {
            filterChain.doFilter(request, response)
        } catch (e: BasicException) {
            exceptionToResponse(e.errorCode, response)
        } catch (e: ExpiredJwtException) {
            exceptionToResponse(ErrorCode.JWT_EXPIRED_ERROR, response)
        } catch (e: Exception) {
            e.printStackTrace()
            exceptionToResponse(ErrorCode.UNEXPECTED_ERROR, response)
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