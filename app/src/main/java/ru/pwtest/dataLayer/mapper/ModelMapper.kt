package ru.pwtest.dataLayer.mapper

import ru.pwtest.dataLayer.model.*
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


    fun mapToEntity(from: LoggedUserModel) = UserEntity(
        id = from.loggedUser.id,
        name = from.loggedUser.username,
        balance = from.loggedUser.balance,
        email = from.loggedUser.email
    )

    fun mapToEntity(loggedUserTransactionsModel: LoggedUserTransactionsModel): MutableList<TransactionEntity> {
        val transactionEntities = mutableListOf<TransactionEntity>()
        loggedUserTransactionsModel.transactions?.forEach { transactionEntities.add(mapToEntity(it)) }
        return transactionEntities
    }

    fun mapToEntity(transactionRespModel: CreateTransactionRespModel): TransactionEntity {
        return transactionRespModel.transaction.let {  mapToEntity(it) }
    }
}