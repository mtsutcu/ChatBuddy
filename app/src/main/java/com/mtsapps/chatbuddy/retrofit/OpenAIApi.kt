package com.mtsapps.chatbuddy.retrofit

import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.ApiResponse
import com.mtsapps.chatbuddy.utils.Cons.API_KEY
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIApi {

    @POST("chat/completions")
    @Headers("Authorization: Bearer $API_KEY")
    suspend fun sendRequest(@Body request: ApiRequest): ApiResponse

}