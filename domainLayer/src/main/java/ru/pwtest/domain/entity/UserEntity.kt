package ru.pwtest.domain.entity

data class UserEntity(
        val id: Int,
        val name: String,
        val balance: Int,
        val email: String?)