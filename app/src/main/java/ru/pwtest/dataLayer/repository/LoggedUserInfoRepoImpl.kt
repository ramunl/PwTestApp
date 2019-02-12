package ru.pwtest.dataLayer.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.mapper.ModelMapper
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.domainLayer.entity.UserEntity
import ru.pwtest.domainLayer.repository.LoggedUserInfoRepo
import javax.inject.Inject

class LoggedUserInfoRepoImpl @Inject constructor(
    private val api: AppApi,
    private val modelMapper: ModelMapper
) : LoggedUserInfoRepo {

    override fun getLoggedUserInfo(): Single<UserEntity> {
       return api.loggedUserInfo().map { modelMapper.mapToEntity(it) }
    }
}