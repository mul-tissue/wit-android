package com.multissue.wit.core.network.service

import com.multissue.wit.core.network.model.BaseResponse
import com.multissue.wit.core.network.model.user.request.OnboardingRequest
import com.multissue.wit.core.network.model.user.response.OnboardingResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {

    @GET("v1/users/check/nickname")
    suspend fun checkNicknameDuplicate(
        @Query("value") nickname: String,
    )

    @PATCH("v1/users/onboarding")
    suspend fun completeOnboarding(
        @Body request: OnboardingRequest,
    ): BaseResponse<OnboardingResponse>
}
