package ru.pwtest.domain.usecases.transaction

import io.reactivex.Single
import ru.pwtest.domain.entity.TransactionEntity
import ru.pwtest.domain.repository.TransactionRepo
import ru.pwtest.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepo: TransactionRepo
) : SingleUseCase<CreateTransactionUseCase.Param, TransactionEntity>() {

    override fun build(parameters: Param): Single<TransactionEntity> =
        transactionRepo.createTransaction(parameters.name, parameters.amount)

    data class Param(val name: String, val amount: Int)

}