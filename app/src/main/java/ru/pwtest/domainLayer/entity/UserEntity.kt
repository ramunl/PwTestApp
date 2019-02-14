package ru.pwtest.domainLayer.entity

data class UserEntity(
        val id: Int,
        val name: String,
        val balance: Int,
        val email: String?)