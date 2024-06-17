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
import org.slf4j.LoggerFactory

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BasicException::class)
    fun basicExceptionHandler(e: BasicException): ResponseEntity<BasicResponse.BaseResponse> =
        BasicResponse.error(e.errorCode)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<BasicResponse.BaseResponse> {
        val errorMessage = e.bindingResult.allErrors[0].defaultMessage ?: "데이터가 정상적으로 전달되지 않았습니다."
        return createCustomErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, BasicResponse.BaseStatus.ERROR, e)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodMissMatchExceptionHandler(e: MethodArgumentTypeMismatchException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.METHOD_MISS_MATCH_ERROR, e)

    @ExceptionHandler(NoResourceFoundException::class)
    fun jsonExceptionHandler(e: NoResourceFoundException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.PAGE_NOT_FOUND, e)

    @ExceptionHandler(Exception::class)
    fun unexpectedExceptionHandler(e: Exception): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.UNEXPECTED_ERROR, e)

    @ExceptionHandler(HandlerMethodValidationException::class)
    fun validationFailureExceptionHandler(e: HandlerMethodValidationException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.VALIDATION_FAILED_ERROR, e)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodNotSupportExceptionHandler(e: HttpRequestMethodNotSupportedException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.METHOD_NOT_SUPPORT_ERROR, e)

    @ExceptionHandler(RequestRejectedException::class)
    fun requestRejectionExceptionHandler(e: RequestRejectedException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.REQUEST_REJECTION_ERROR, e)

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun notSupportType(e: HttpMediaTypeNotSupportedException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.NOT_SUPPORT_TYPE_REQUEST, e)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun cannotReadBodyExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<BasicResponse.BaseResponse> =
        createErrorResponse(ErrorCode.WRONG_BODY_ERROR, e)

    private fun createErrorResponse(errorCode: ErrorCode, e: Exception): ResponseEntity<BasicResponse.BaseResponse> {
        logger.error("Exception: ${e.javaClass.simpleName}", e)
        return BasicResponse.error(errorCode)
    }

    private fun createCustomErrorResponse(message: String, status: HttpStatus, baseStatus: BasicResponse.BaseStatus, e: Exception): ResponseEntity<BasicResponse.BaseResponse> {
        logger.error("Exception: ${e.javaClass.simpleName}", e)
        return BasicResponse.customStatus(MsgResponse(message), status, baseStatus)
    }
}
