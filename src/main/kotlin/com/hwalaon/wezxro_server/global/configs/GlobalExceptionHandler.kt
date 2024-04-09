package com.hwalaon.wezxro_server.global.configs

import com.hwalaon.wezxro_server.global.common.basic.exception.BasicException
import com.hwalaon.wezxro_server.global.common.basic.exception.ErrorCode
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.common.basic.response.MsgResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException) = BasicResponse.error(e.errorCode)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validExceptionHandler(e: MethodArgumentNotValidException) =
        BasicResponse.customStatus(
            MsgResponse(e.bindingResult.allErrors[0].defaultMessage ?: "데이터가 정상적으로 전달되지 않았습니다."),
            HttpStatus.BAD_REQUEST,
            BasicResponse.BaseStatus.ERROR)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodMissMatchExceptionHandler() = BasicResponse.error(errorInfo = ErrorCode.METHOD_MISS_MATCH_ERROR)

    @ExceptionHandler(NoResourceFoundException::class)
    fun jsonExceptionHandler(e: NoResourceFoundException) =
        BasicResponse.error(ErrorCode.PAGE_NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun unexpectedExceptionHandler(e: Exception): ResponseEntity<BasicResponse.BaseResponse> {
        e.printStackTrace()
        return BasicResponse.error(ErrorCode.UNEXPECTED_ERROR)
    }

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun validationFailureExceptionHandler() =
        BasicResponse.error(ErrorCode.VALIDATION_FAILED_ERROR)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodNotSupportExceptionHandler() =
        BasicResponse.error(ErrorCode.METHOD_NOT_SUPPORT_ERROR)

    @ExceptionHandler(RequestRejectedException::class)
    fun requestRejectionExceptionHandler() =
        BasicResponse.error(ErrorCode.REQUEST_REJECTION_ERROR)

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun notSupportType() = BasicResponse.error(ErrorCode.NOT_SUPPORT_TYPE_REQUEST)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun cannotReadBodyExceptionHandler(e: HttpMessageNotReadableException) =
        BasicResponse.error(ErrorCode.WRONG_BODY_ERROR)
}