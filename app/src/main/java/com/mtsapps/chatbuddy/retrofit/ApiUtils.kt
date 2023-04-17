package com.mtsapps.chatbuddy.retrofit

import com.mtsapps.chatbuddy.utils.Cons.BASE_URL

class ApiUtils {
    companion object{
        fun getOpenAIApi() : OpenAIApi{
            return RetrofitClient.getClient(BASE_URL).create(OpenAIApi::class.java)
        }
    }
}