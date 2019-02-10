package ru.pwtest.pwapp.model

data class TransactionViewModel(
        val id: Int,
        val date: Int?,
        val username: String?,
        val amount: Int?,
        val balance: Int?)