package com.multissue.wit.core.network.service

import com.multissue.wit.core.network.model.BaseResponse
import com.multissue.wit.core.network.model.terms.request.AgreeTermsRequest
import com.multissue.wit.core.network.model.terms.response.AgreeTermsResponse
import com.multissue.wit.core.network.model.terms.response.TermsListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TermsService {

    @GET("v1/terms/active")
    suspend fun getActiveTerms(): BaseResponse<TermsListResponse>

    @POST("v1/users/terms")
    suspend fun agreeTerms(
        @Body request: AgreeTermsRequest,
    ): BaseResponse<AgreeTermsResponse>
}
