package ru.pwtest.domainLayer.repository
import io.reactivex.Single
import ru.pwtest.domainLayer.entity.TransactionEntity

interface TransactionRepo {

    fun getTransactions(): Single<List<TransactionEntity>>
}