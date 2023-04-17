package com.mtsapps.chatbuddy.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "chat_table")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val messages : List<CustomMessage>
) : Serializable
