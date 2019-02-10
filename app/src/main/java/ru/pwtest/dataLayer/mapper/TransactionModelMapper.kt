package ru.pwtest.dataLayer.mapper

import ru.pwtest.dataLayer.model.TransactionModel
import ru.pwtest.delegate.date.DateDelegate
import ru.pwtest.domainLayer.entity.TransactionEntity
import javax.inject.Inject

class TransactionModelMapper @Inject constructor(
    private val dateDelegate: DateDelegate
) {


    fun mapToEntity(from: TransactionModel) = TransactionEntity(
        id = from.id,
        username = from.username,
        balance = from.balance,
        amount = from.amount,
        date = from.date
    )

}