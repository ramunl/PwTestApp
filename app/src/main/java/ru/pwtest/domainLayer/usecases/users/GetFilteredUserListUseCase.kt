package ru.pwtest.domainLayer.usecases.users

import io.reactivex.Single
import ru.pwtest.domainLayer.entity.UserEntity
import ru.pwtest.domainLayer.repository.FilteredUserListRepo
import ru.pwtest.domainLayer.usecases.base.SingleUseCase
import javax.inject.Inject

class GetFilteredUserListUseCase @Inject constructor(
        private val transactionRepo: FilteredUserListRepo
) : SingleUseCase<GetFilteredUserListUseCase.Param, List<UserEntity>>() {

    override fun build(parameters: Param): Single<List<UserEntity>> = transactionRepo.getFilteredUserList(filter = parameters.filter)

    data class Param(val filter: String? = null)




}