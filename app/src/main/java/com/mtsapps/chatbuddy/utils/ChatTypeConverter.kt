package com.mtsapps.chatbuddy.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage

class ChatTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun messageToString(messages: List<CustomMessage>) : String{
        return gson.toJson(messages)
    }

    @TypeConverter
    fun stringToMessage(messageString: String) : List<CustomMessage>{
        val objectTye = object  : TypeToken<List<CustomMessage>>(){}.type
        return gson.fromJson(messageString,objectTye)
    }

}