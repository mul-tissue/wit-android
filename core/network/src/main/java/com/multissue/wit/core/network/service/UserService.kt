package com.multissue.wit.core.network.service

import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("v1/users/check/nickname")
    suspend fun checkNicknameDuplicate(
        @Query("value") nickname: String,
    )
}
