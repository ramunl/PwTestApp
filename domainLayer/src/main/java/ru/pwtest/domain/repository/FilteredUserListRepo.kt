package ru.pwtest.domain.repository

import io.reactivex.Single
import ru.pwtest.domain.entity.UserEntity

interface FilteredUserListRepo {
    fun getFilteredUserList(filter: String?): Single<List<UserEntity>>
}