package com.multissue.wit.core.network.service

import com.multissue.wit.core.network.model.BaseResponse
import com.multissue.wit.core.network.model.auth.request.RefreshTokenRequest
import com.multissue.wit.core.network.model.auth.request.SocialLoginRequest
import com.multissue.wit.core.network.model.auth.response.RefreshTokenResponse
import com.multissue.wit.core.network.model.auth.response.SocialLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth/login/social")
    suspend fun socialLogin(
        @Body request: SocialLoginRequest,
    ): BaseResponse<SocialLoginResponse>

    @POST("v1/auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest,
    ): BaseResponse<RefreshTokenResponse>

    @POST("v1/auth/logout")
    suspend fun logout()
}
