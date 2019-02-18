package ru.pwtest.domain.usecases.base

import io.reactivex.Single

abstract class SingleUseCase<in PARAMS, RESULT> {

    fun execute(parameters: PARAMS) = build(parameters)

    abstract fun build(parameters: PARAMS): Single<RESULT>
}

