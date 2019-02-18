package ru.pwtest.domain.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.domain.entity.UserEntity
import ru.pwtest.domain.mapper.ModelMapper
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