package ru.pwtest.pwapp.mapper

import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.domainLayer.entity.UserEntity
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.model.UserViewModel
import javax.inject.Inject

class EntityViewModelMapper @Inject constructor() {

    fun mapToViewModel(from: TransactionEntity) = TransactionViewModel(
        id = from.id,
        username = from.username,
        balance = from.balance,
        amount = from.amount,
        date = from.date
    )

    fun mapToViewModel(from: UserEntity) = UserViewModel(
        id = from.id,
        name = from.name,
        balance = from.balance,
        email = from.email
    )
}