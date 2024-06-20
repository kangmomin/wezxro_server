package com.hwalaon.wezxro_server.domain.account.controller

import com.hwalaon.wezxro_server.domain.account.controller.request.*
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountDetailResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountExportResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountListResponse
import com.hwalaon.wezxro_server.domain.account.controller.response.AccountListResponse.AccountDto
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.service.CommandAccountService
import com.hwalaon.wezxro_server.domain.account.service.QueryAccountService
import com.hwalaon.wezxro_server.global.common.basic.response.BasicResponse
import com.hwalaon.wezxro_server.global.security.principal.PrincipalDetails
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import kotlin.reflect.full.memberProperties

@RestController
@RequestMapping("/admin/u")
class AccountAdminController(
    private val queryAccountService: QueryAccountService,
    private val commandAccountService: CommandAccountService,
) {
    private val logger = LoggerFactory.getLogger(AccountAdminController::class.java)

    @PatchMapping("/update")
    fun updateInfo(
        @RequestBody @Valid updateAccountRequest: UpdateAccountRequest,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val account = updateAccountRequest.toDomain()
        account.clientId = principalDetails.account.clientId

        commandAccountService.updateAccountInfo(account)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / target - ${account.userId}")

        return BasicResponse.ok("계정 정보를 성공적으로 변경하였습니다.")
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.deleteAccount(id, principalDetails.account.clientId!!)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Delete: by - ${principalDetails.account.userId!!} / target - $id")

        return BasicResponse.ok("삭제되었습니다.")
    }

    @PostMapping("/detail/{id}")
    fun accountDetails(
        @PathVariable("id") id: Long,
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) =
        BasicResponse.ok(
            AccountDetailResponse.fromDomain(
                queryAccountService.adminDetail(id, principalDetails.account.clientId!!)
            )
        )

    @GetMapping("/list")
    fun accountList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails
    ) = BasicResponse.ok(queryAccountService.list(principalDetails.account.clientId))

    @GetMapping("/static-rate/{userId}")
    fun staticRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long
    ) = BasicResponse.ok(queryAccountService.getStaticRate(principalDetails.account.clientId, userId))

    @PatchMapping("/static-rate/update/{userId}")
    fun updateStaticRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long,
        @RequestBody @Valid updateStaticRate: UpdateStaticRate
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.updateStaticRate(
            updateStaticRate.staticRate!!, userId,
            principalDetails.account.clientId!!
        )

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / target - $userId / static rate(${updateStaticRate.staticRate})")

        return BasicResponse.ok("전체 감가액을 지정하였습니다.")
    }

    @PostMapping("/custom-rate")
    fun addCustomRate(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid addCustomRateRequest: AddCustomRateRequest
    ) = commandAccountService.storeCustomRate(principalDetails.account.clientId!!, addCustomRateRequest).run {
        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / target - ${addCustomRateRequest.userId} / custom rate")

        BasicResponse.ok("개별 감가액의 업데이트가 정상적으로 처리 되었습니다.")
    }

    @DeleteMapping("/custom-rate/all/{userId}")
    fun deleteCustomRateAll(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.deleteCustomRate(userId, principalDetails.account.clientId!!)
        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Delete: by - ${principalDetails.account.userId!!} / target - $userId / custom rate")

        return BasicResponse.ok("유저의 모든 개별 감가액을 설정하였습니다.")
    }

    @GetMapping("/custom-rate/{userId}")
    fun customRateList(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val crs = queryAccountService.customRateByUserId(principalDetails.account.clientId!!, userId)

        return BasicResponse.ok(crs)
    }

    @PatchMapping("/money/add")
    fun addMoney(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateMoneyRequest: UpdateMoneyRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.addFund(
            updateMoneyRequest,
            principalDetails.account.clientId!!,
            principalDetails.account.userId!!
        )

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / " +
                "target - ${updateMoneyRequest.userId} / " +
                "add fund(${updateMoneyRequest.balance})")

        return BasicResponse.ok("유저의 잔액이 추가되었습니다.")
    }

    @PatchMapping("/money/set")
    fun setMoney(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateMoneyRequest: UpdateMoneyRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.setMoney(
            updateMoneyRequest,
            principalDetails.account.clientId!!,
            principalDetails.account.userId!!
        )

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / " +
                "target - ${updateMoneyRequest.userId} / set balance / ${updateMoneyRequest.balance}")

        return BasicResponse.ok("잔액을 설정하였습니다.")
    }

    @PatchMapping("/password/update")
    fun updatePassword(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updatePasswordRequest: UpdatePasswordRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.setPassword(
            updatePasswordRequest,
            principalDetails.account.userId!!,
            principalDetails.account.clientId!!
        )

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / " +
                "target - ${updatePasswordRequest.userId} / set password")

        return BasicResponse.ok("비밀번호를 설정하였습니다.")
    }

    @PatchMapping("/status/update")
    fun updateStatus(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid updateStatusRequest: UpdateStatusRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        commandAccountService.updateStatus(
            principalDetails.account.clientId!!, updateStatusRequest,
        )

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Update: by - ${principalDetails.account.userId!!} / " +
                "target - ${updateStatusRequest.userId} / status")

        return BasicResponse.ok("Status를 업데이트 하였습니다.")
    }

    @PostMapping("/mail-send")
    fun sendMail(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @RequestBody @Valid sendMailRequest: SendMailRequest
    ): ResponseEntity<BasicResponse.BaseResponse> {
        queryAccountService.sendMail(sendMailRequest, principalDetails.account.clientId!!)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "Mail: by - ${principalDetails.account.userId!!} / target - ${sendMailRequest.email}")

        return BasicResponse.ok("메일을 전송하였습니다.")
    }

    @PostMapping("/view-user/{userId}")
    fun viewUser(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        @PathVariable userId: Long,
    ): ResponseEntity<BasicResponse.BaseResponse> {
        val jwt = queryAccountService.viewUserLogin(userId, principalDetails.account.clientId!!)

        logger.info("clientId:${principalDetails.account.clientId!!} " +
                "View: by - ${principalDetails.account.userId!!} / target - $userId")

        return BasicResponse.ok(jwt)
    }

    @GetMapping("/export")
    fun exportToExcel(
        @AuthenticationPrincipal principalDetails: PrincipalDetails,
        response: HttpServletResponse
    ) {
        val list = queryAccountService.exportList(principalDetails.account.clientId!!)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Users")

        // Header row
        val headerRow = sheet.createRow(0)

        AccountExportResponse::class.memberProperties.forEachIndexed { idx, it ->
            headerRow.createCell(idx).setCellValue(it.name)
        }

        // Data rows
        list.forEachIndexed { index, user ->
            val row = sheet.createRow(index + 1)
            AccountExportResponse::class.memberProperties.mapIndexed {idx, it ->
                row.createCell(idx).setCellValue(it.get(user).toString())
            }
        }

        // Set response headers
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        response.setHeader("Content-Disposition", "attachment; filename=유저_정보.xlsx")

        // Write workbook to output stream
        workbook.write(response.outputStream)
        workbook.close()
    }
}