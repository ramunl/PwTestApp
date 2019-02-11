package ru.pwtest.dataLayer.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.mapper.ModelMapper
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.delegate.date.DateDelegate
import ru.pwtest.domainLayer.entity.TransactionEntity
import ru.pwtest.domainLayer.repository.TransactionRepo
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
    private val api: AppApi,
    private val modelMapper: ModelMapper,
    private val dateDelegate: DateDelegate
) : TransactionRepo {

    override fun getTransaction(): Single<List<TransactionEntity>> =
            getNetworkTransactions().toFlowable()
            .filter { it.isNotEmpty() }
            .first(emptyList())


    private fun getNetworkTransactions(): Single<List<TransactionEntity>> {
       return api.getTransactions()
            .flattenAsObservable { it }
            .map { modelMapper.mapToEntity(it) }
            .toList()
    }


    /*private fun getUserTransactions(): Single<MutableList<TransactionEntity>>?{
        return api.getTransactions()
            .map { model -> model.transactions?.forEach { modelMapper.mapToEntity(it) } }
            .cast()
    }*/
}