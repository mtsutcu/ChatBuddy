package com.mtsapps.chatbuddy.ui.homefragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel  @Inject constructor(var repository: Repository)  : ViewModel() {
    private val _viewState = MutableStateFlow(HomePageViewState(repository.context))
    val viewState: StateFlow<HomePageViewState> = _viewState.asStateFlow()

    init {
        getChat(1)
    }

    fun sendRequest(request: ApiRequest) {
        val totalMessage = _viewState.value.message?.toMutableList()
        totalMessage?.add(request.messages[0])
        _viewState.update {
            it.copy(
                message = totalMessage
            )
        }

        viewModelScope.launch {
            repository.sendRequest(request).collect { res ->
                val totalMessage = _viewState.value.message?.toMutableList()
                totalMessage?.add(res!!.choices[0].message)
                _viewState.update {
                    it.copy(
                        message = totalMessage

                    )
                }
            }
        }
    }

    fun addChat() {
        val title = viewState.value.message!![1].content
        val messages = viewState.value.message

        viewModelScope.launch {
            repository.addChat(Chat(0, title, messages!!)).collect { items ->
            }
        }

    }


    fun getChat(id: Int) {
        viewModelScope.launch {
            repository.getChat(id).collectLatest { ch ->
                Log.e("chat", "$ch")
            }

        }
    }


}

data class HomePageViewState(
    val context: Context,
    val message: List<CustomMessage>? = mutableListOf(
        CustomMessage(
            "assistant",
           context.getString(R.string.howCanAssit)
        )
    )
)
