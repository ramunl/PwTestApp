package ru.pwtest.domainLayer.entity

data class TransactionEntity(
        val id: Int,
        val date: String,
        val username: String,
        val amount: Int,
        val balance: Int)