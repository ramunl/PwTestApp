package ru.pwtest.domainLayer.entity

data class TransactionEntity(
        val id: Int,
        val date: Int?,
        val username: String?,
        val amount: Int?,
        val balance: Int?)