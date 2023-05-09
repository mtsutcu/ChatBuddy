package com.mtsapps.chatbuddy.datasource

import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.room.ChatDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LocalDataSource(private val chatDao: ChatDao) {

    suspend fun addChat(chat: Chat) : Flow<List<CustomMessage>?>{
        chatDao.addChat(chat)
      return  flow { emit(withContext(Dispatchers.IO) {
            mutableListOf(
                CustomMessage(
                    "assistant",
                    "How can I assist you?"
                )
            )

        })
    }}

     fun getChat(id : Int) : Flow<Chat> {
        return  chatDao.getChat(id)
    }

    fun getAllChat() : Flow<List<Chat>>{
        return chatDao.getAllChat()
    }

    suspend fun deleteChat(chat: Chat) {
        chatDao.deleteChat(chat.id)
    }
}