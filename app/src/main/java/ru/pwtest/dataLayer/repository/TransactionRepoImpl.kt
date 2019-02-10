package ru.pwtest.dataLayer.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.mapper.TransactionModelMapper
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.delegate.date.DateDelegate
import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.domainLayer.repository.TransactionRepo
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val api: AppApi,
    private val transactionModelMapper: TransactionModelMapper,
    private val dateDelegate: DateDelegate
) : TransactionRepo {

    override fun getTransaction(): Single<List<TransactionEntity>> =
            getNetworkTransactions().toFlowable()
            .filter { it.isNotEmpty() }
            .first(emptyList())


    private fun getNetworkTransactions(): Single<List<TransactionEntity>> {
       return api.getTransactions()
            .flattenAsObservable { it }
            .map { transactionModelMapper.mapToEntity(it) }
            .toList()
    }


    /*private fun getUserTransactions(): Single<MutableList<TransactionEntity>>?{
        return api.getTransactions()
            .map { model -> model.transactions?.forEach { transactionModelMapper.mapToEntity(it) } }
            .cast()
    }*/
}