package ru.pwtest.domainLayer.repository

import io.reactivex.Single
import ru.pwtest.domainLayer.entity.UserEntity

interface FilteredUserListRepo {
    fun getFilteredUserList(filter: String?): Single<List<UserEntity>>
}