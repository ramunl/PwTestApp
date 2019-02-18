package ru.pwtest.domain.repository
import io.reactivex.Single
import ru.pwtest.domain.entity.TransactionEntity

interface TransactionRepo {

    fun getTransactions(): Single<List<TransactionEntity>>
    fun createTransaction(name: String, amount: Int): Single<TransactionEntity>
}