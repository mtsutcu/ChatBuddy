package com.mtsapps.chatbuddy.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val myObject: String,
    @SerializedName("created")
    val created: Int,
    @SerializedName("model")
    val model: String,
    @SerializedName("usage")
    val usage: Usage,
    @SerializedName("choices")
    val choices: List<Choice>,
)