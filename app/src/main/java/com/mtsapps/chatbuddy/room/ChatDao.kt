package com.mtsapps.chatbuddy.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun addChat(chat :Chat)

    @Query("SELECT * FROM chat_table WHERE id = :id ")
    fun getChat(id : Int) : Flow<Chat>

    @Query("SELECT * FROM chat_table ")
     fun getAllChat() : Flow<List<Chat>>

    @Query("DELETE FROM chat_table WHERE id = :id")
    suspend fun deleteChat(id: Int)
}