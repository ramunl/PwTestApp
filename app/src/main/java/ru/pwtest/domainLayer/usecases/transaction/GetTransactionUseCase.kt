package ru.pwtest.domainLayer.usecases.transaction

import io.reactivex.Single
import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.domainLayer.repository.TransactionRepo
import ru.pwtest.domainLayer.usecases.base.SingleUseCase
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
        private val transactionRepo: TransactionRepo
) : SingleUseCase<GetTransactionUseCase.Param, List<TransactionEntity>>() {

    override fun build(parameters: Param): Single<List<TransactionEntity>> = transactionRepo.getTransactions()

    data class Param(val count: Int = 0)

}