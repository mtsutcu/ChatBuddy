package com.mtsapps.chatbuddy.models

import com.google.gson.annotations.SerializedName


data class CustomMessage(

    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String,

)

