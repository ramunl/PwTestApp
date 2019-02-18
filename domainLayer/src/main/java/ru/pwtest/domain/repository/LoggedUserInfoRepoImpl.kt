package ru.pwtest.domain.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.domain.entity.UserEntity
import ru.pwtest.domain.mapper.ModelMapper
import javax.inject.Inject

class LoggedUserInfoRepoImpl @Inject constructor(
    private val api: AppApi,
    private val modelMapper: ModelMapper
) : LoggedUserInfoRepo {

    override fun getLoggedUserInfo(): Single<UserEntity> {
       return api.loggedUserInfo().map { modelMapper.mapToEntity(it) }
    }
}