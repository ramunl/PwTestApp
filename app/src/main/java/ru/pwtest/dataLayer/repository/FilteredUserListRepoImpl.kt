package ru.pwtest.dataLayer.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.mapper.ModelMapper
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.domainLayer.entity.UserEntity
import ru.pwtest.domainLayer.repository.FilteredUserListRepo
import javax.inject.Inject

class FilteredUserListRepoImpl @Inject constructor(
    private val api: AppApi,
    private val modelMapper: ModelMapper
) : FilteredUserListRepo {


    private fun getNetworkUserList(filter: String?) = api.filteredUserList(filter)
    .flattenAsObservable { it }
    .map { modelMapper.mapToEntity(it) }
    .toList()

    override fun getFilteredUserList(filter: String?): Single<List<UserEntity>> =
        getNetworkUserList(filter).toFlowable()
            .filter { it.isNotEmpty() }
            .first(emptyList())
}