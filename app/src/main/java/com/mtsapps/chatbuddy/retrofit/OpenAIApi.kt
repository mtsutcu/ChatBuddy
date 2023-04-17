package com.mtsapps.chatbuddy.retrofit

import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {

    @POST("chat/completions")
    @Headers("Authorization: Bearer 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")
    suspend fun sendRequest(@Body request: ApiRequest): ApiResponse

}