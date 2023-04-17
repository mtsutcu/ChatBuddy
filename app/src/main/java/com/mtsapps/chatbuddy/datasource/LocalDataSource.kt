package com.mtsapps.chatbuddy.datasource

import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.room.ChatDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val chatDao: ChatDao) {

    suspend fun addChat(chat: Chat){
        chatDao.addChat(chat)
    }

     fun getChat(id : Int) : Flow<Chat> {
        return  chatDao.getChat(id)
    }

    fun getAllChat() : Flow<List<Chat>>{
        return chatDao.getAllChat()
    }
}