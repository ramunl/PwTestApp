package ru.pwtest.pwapp.model

data class TransactionViewModel(
        val id: Int,
        val date: String,
        val username: String,
        val amount: Int,
        val balance: Int)