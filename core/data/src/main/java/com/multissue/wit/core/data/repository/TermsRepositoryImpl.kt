package com.multissue.wit.core.data.repository

import com.multissue.wit.core.data.mapper.toDomain
import com.multissue.wit.core.domain.exception.WitException
import com.multissue.wit.core.domain.model.terms.TermItem
import com.multissue.wit.core.domain.repository.TermsRepository
import com.multissue.wit.core.network.Dispatcher
import com.multissue.wit.core.network.WitDispatchers
import com.multissue.wit.core.network.model.ApiResponse
import com.multissue.wit.core.network.model.terms.request.AgreeTermsRequest
import com.multissue.wit.core.network.model.terms.request.TermAgreement
import com.multissue.wit.core.network.service.TermsService
import com.multissue.wit.core.network.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TermsRepositoryImpl @Inject constructor(
    private val termsService: TermsService,
    @Dispatcher(WitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TermsRepository {

    override suspend fun getActiveTerms(): Result<List<TermItem>> =
        withContext(ioDispatcher) {
            when (val response = safeApiCall { termsService.getActiveTerms() }) {
                is ApiResponse.Success -> {
                    val terms = response.data.data?.terms?.map { it.toDomain() } ?: emptyList()
                    Result.success(terms)
                }
                is ApiResponse.Failure -> Result.failure(
                    WitException.HttpException(response.code, response.message)
                )
                is ApiResponse.NetworkError -> Result.failure(
                    WitException.NetworkException(response.throwable)
                )
            }
        }

    override suspend fun agreeTerms(agreements: List<Pair<String, Boolean>>): Result<String> =
        withContext(ioDispatcher) {
            val request = AgreeTermsRequest(
                terms = agreements.map { (termId, agreed) ->
                    TermAgreement(termId = termId, agreed = agreed)
                }
            )
            when (val response = safeApiCall { termsService.agreeTerms(request) }) {
                is ApiResponse.Success -> {
                    val userStatus = response.data.data?.userStatus ?: ""
                    Result.success(userStatus)
                }
                is ApiResponse.Failure -> Result.failure(
                    WitException.HttpException(response.code, response.message)
                )
                is ApiResponse.NetworkError -> Result.failure(
                    WitException.NetworkException(response.throwable)
                )
            }
        }
}
