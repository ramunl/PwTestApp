package ru.pwtest.dataLayer.mapper

import ru.pwtest.dataLayer.model.TransactionModel
import ru.pwtest.dataLayer.model.UserModel
import ru.pwtest.delegate.date.DateDelegate
import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.domainLayer.entity.UserEntity
import javax.inject.Inject

class ModelMapper @Inject constructor(
    private val dateDelegate: DateDelegate
) {


    fun mapToEntity(from: TransactionModel) = TransactionEntity(
        id = from.id,
        username = from.username,
        balance = from.balance,
        amount = from.amount,
        date = from.date
    )

    fun mapToEntity(from: UserModel) = UserEntity(
        id = from.id,
        name = from.username,
        balance = from.balance,
        email = from.email
    )
}