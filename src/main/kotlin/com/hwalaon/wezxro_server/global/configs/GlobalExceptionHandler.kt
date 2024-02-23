package com.hwalaon.wezxro_server.global.configs

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException) = BasicResponse.error(e.errorCode)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonExceptionHandler(e: HttpMessageNotReadableException) =
        BasicResponse.error(ErrorCode.NON_BODY_ERROR)

    @ExceptionHandler(NoResourceFoundException::class)
    fun jsonExceptionHandler(e: NoResourceFoundException) =
        BasicResponse.error(ErrorCode.PAGE_NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun unexpectedExceptionHandler(e: Exception): ResponseEntity<BasicResponse.BaseResponse> {
        e.printStackTrace()
        return BasicResponse.error(ErrorCode.UNEXPECTED_ERROR)
    }
}