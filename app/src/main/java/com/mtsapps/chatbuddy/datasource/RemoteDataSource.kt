package com.mtsapps.chatbuddy.datasource

import android.util.Log
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.ApiResponse
import com.mtsapps.chatbuddy.retrofit.OpenAIApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteDataSource(var openAIApi: OpenAIApi) {
    suspend fun senRequest(request: ApiRequest): Flow<ApiResponse?> {
        val res : ApiResponse
        try {
            withContext(Dispatchers.IO){
                res =   openAIApi.sendRequest(request)
              //  addMessage(RoomMessage(0,res.choices.get(0).message.role,res.choices.get(0).message.content))

            }
            return flow { emit(withContext(Dispatchers.IO) {
                res

            }) }
        }catch (e: IOException){
            Log.e("exeep",e.message.toString())
        }

        return flow { null }


    }

}