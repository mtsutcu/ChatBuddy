package com.mtsapps.chatbuddy.models

import com.google.gson.annotations.SerializedName

data class Choice(
    @SerializedName("message")
    val message: CustomMessage,
    @SerializedName("finish_reason")
    val finish_reason: String,
    @SerializedName("index")
    val index: Int)