package com.hwalaon.wezxro_server.domain.account.service

import com.hwalaon.wezxro_server.domain.account.controller.request.JoinRequest
import com.hwalaon.wezxro_server.domain.account.model.Account
import com.hwalaon.wezxro_server.domain.account.model.constant.AccountStatus
import com.hwalaon.wezxro_server.domain.account.persistence.AccountPersistenceAdapter
import com.hwalaon.wezxro_server.global.BasicResponse
import com.hwalaon.wezxro_server.global.exception.dto.MsgResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class CommandAccountServiceTest {

    // Joining a new account with valid data should return a success message
    @Test
    fun test_joinAccount_validData_successMessage() {
        // Arrange
        val joinRequest = JoinRequest("John Doe", "password", "john.doe@example.com")
        val accountPersistenceAdapter = mock(AccountPersistenceAdapter::class.java)
        val commandAccountService = CommandAccountService(accountPersistenceAdapter)
        val expectedResponse = BasicResponse.created(MsgResponse("회원가입에 성공하였습니다."))

        // Act
        val actualResponse = commandAccountService.join(joinRequest)

        // Assert
        assertEquals(expectedResponse, actualResponse)
        verify(accountPersistenceAdapter).join(any())

    }

    // Updating an existing account with valid data should return a success message
    @Test
    fun test_updateAccountInfo_validData_successMessage() {
        // Arrange
        val account = Account(1, "John Doe", "password", "john.doe@example.com", "random", 100.0, AccountStatus.ACTIVE)
        val accountPersistenceAdapter = mock(AccountPersistenceAdapter::class.java)
        val commandAccountService = CommandAccountService(accountPersistenceAdapter)
        val expectedResponse = BasicResponse.ok(MsgResponse("John Doe님의 계정 정보를 성공적으로 변경하였습니다."))

        // Act
        commandAccountService.updateAccountInfo(account)
        val actualResponse = commandAccountService.updateAccountInfo(account)

        // Assert
        assertEquals(expectedResponse, actualResponse)
        verify(accountPersistenceAdapter, times(2)).updateInfo(account)
    }

    // Checking if an account exists with valid data should return true or false
    @Test
    fun test_validAccount_existingAccount_returnTrueOrFalse() {
        // Arrange
        val account = Account(1, "John Doe", "password", "john.doe@example.com", "random", 100.0, AccountStatus.ACTIVE)
        val accountPersistenceAdapter = mock(AccountPersistenceAdapter::class.java)
        val commandAccountService = CommandAccountService(accountPersistenceAdapter)
        val expectedResponse = false

        // Act
        val actualResponse = commandAccountService.validAccount(account)

        // Assert
        assertEquals(expectedResponse, actualResponse)
        verify(accountPersistenceAdapter).isExistAccount(account.email!!)
        verify(accountPersistenceAdapter).isExistName(account.name!!)
    }



    private fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}