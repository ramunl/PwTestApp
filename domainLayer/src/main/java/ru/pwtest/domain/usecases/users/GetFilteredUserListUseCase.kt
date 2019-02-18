package ru.pwtest.domain.usecases.users

import io.reactivex.Single
import ru.pwtest.domain.entity.UserEntity
import ru.pwtest.domain.repository.FilteredUserListRepo
import ru.pwtest.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class GetFilteredUserListUseCase @Inject constructor(
        private val transactionRepo: FilteredUserListRepo
) : SingleUseCase<GetFilteredUserListUseCase.Param, List<UserEntity>>() {

    override fun build(parameters: Param): Single<List<UserEntity>> = transactionRepo.getFilteredUserList(filter = parameters.filter)

    data class Param(val filter: String? = null)




}