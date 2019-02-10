package ru.pwtest.pwapp.mapper

import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.pwapp.model.TransactionViewModel
import javax.inject.Inject

class TransactionViewModelMapper @Inject constructor() {

    fun mapToViewModel(from: TransactionEntity) = TransactionViewModel(
        id = from.id,
        username = from.username,
        balance = from.balance,
        amount = from.amount,
        date = from.date
    )

}