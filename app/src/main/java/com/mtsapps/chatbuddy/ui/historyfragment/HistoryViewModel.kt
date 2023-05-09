package com.mtsapps.chatbuddy.ui.historyfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor (private val repository: Repository) : ViewModel() {
    private val _viewState = MutableStateFlow(HistoryPageState())
    val viewState: StateFlow<HistoryPageState> = _viewState.asStateFlow()

    init {
        getAllChats()
    }

    fun getAllChats() {
        viewModelScope.launch {
            repository.getAllChat().collectLatest { chats->
                _viewState.update {
                    it.copy(
                        chats = chats
                    )
                }
            }
        }
    }

    fun deleteChat(chat: Chat){
        viewModelScope.launch {
            repository.deleteChat(chat)
        }
    }

}

data class HistoryPageState(
    val chats: List<Chat>? = null
)