package com.mtsapps.chatbuddy.models

import com.google.gson.annotations.SerializedName

data class ApiRequest(@SerializedName("model")
                      val model : String ="gpt-3.5-turbo",
                      @SerializedName("messages")
                      val messages : List<CustomMessage>) {
}