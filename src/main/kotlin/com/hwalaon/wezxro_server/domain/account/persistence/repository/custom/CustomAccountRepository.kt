package com.hwalaon.wezxro_server.domain.account.persistence.repository.custom

interface CustomAccountRepository {

    /**
     * return ture when account is already exist
     * otherwise false
     */
    fun isExistAccount(email: String): Boolean
}