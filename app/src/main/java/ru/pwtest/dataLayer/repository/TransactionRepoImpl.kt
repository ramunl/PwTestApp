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

    override fun createTransaction(name: String, amount: Int): Single<TransactionEntity> {
        return api.createTransaction(name, amount).map {  modelMapper.mapToEntity(it)}
    }

    override fun getTransactions(): Single<List<TransactionEntity>> =
            getNetworkTransactions().toFlowable()
            .filter { it.isNotEmpty() }
            .first(emptyList())


    private fun getNetworkTransactions(): Single<List<TransactionEntity>> {
       return api.getTransactions().map { modelMapper.mapToEntity(it) }
    }

}