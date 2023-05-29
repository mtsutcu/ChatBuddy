package com.mtsapps.chatbuddy.datasource

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.ApiResponse
import com.mtsapps.chatbuddy.retrofit.OpenAIApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteDataSource(var openAIApi: OpenAIApi,var context: Context) {
    suspend fun senRequest(request: ApiRequest): Flow<ApiResponse?> {
        val res : ApiResponse
        try {
            withContext(Dispatchers.IO){
                res =   openAIApi.sendRequest(request)
            }
            return flow { emit(withContext(Dispatchers.IO) {
                res

            }) }
        }catch (e: IOException){
            Toast.makeText(context,context.getString(R.string.checkInternet),Toast.LENGTH_LONG).show()
        }

        return flow { null }


    }

}