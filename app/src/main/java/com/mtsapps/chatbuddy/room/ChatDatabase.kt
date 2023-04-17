package com.mtsapps.chatbuddy.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.utils.ChatTypeConverter

@Database(
    entities = [Chat::class],
    version = 1,

)
@TypeConverters(ChatTypeConverter::class)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao() : ChatDao
}