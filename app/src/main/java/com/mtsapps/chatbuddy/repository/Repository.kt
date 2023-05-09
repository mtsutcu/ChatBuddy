package com.mtsapps.chatbuddy.repository

import android.content.Context
import com.mtsapps.chatbuddy.datasource.LocalDataSource
import com.mtsapps.chatbuddy.datasource.RemoteDataSource
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.ApiResponse
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import kotlinx.coroutines.flow.Flow

class Repository(var remoteDataSource: RemoteDataSource,var localDataSource: LocalDataSource,var context: Context) {
    suspend fun sendRequest(request: ApiRequest) : Flow<ApiResponse?> = remoteDataSource.senRequest(request)
    suspend fun addChat(chat : Chat) : Flow<List<CustomMessage>?> = localDataSource.addChat(chat)
    fun getChat(id:Int) = localDataSource.getChat(id)
    fun getAllChat() = localDataSource.getAllChat()
    suspend fun deleteChat(chat: Chat) = localDataSource.deleteChat(chat)

}