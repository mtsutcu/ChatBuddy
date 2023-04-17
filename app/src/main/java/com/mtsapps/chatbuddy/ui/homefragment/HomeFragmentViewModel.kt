package com.mtsapps.chatbuddy.ui.homefragment

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.Chat
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(var repository: Repository) : ViewModel() {
    private val _viewState = MutableStateFlow(HomePageViewState())
    val viewState: StateFlow<HomePageViewState> = _viewState.asStateFlow()

    init {
        getChat(1)
    }

    fun sendRequest(request: ApiRequest) {
        var totalMessage = _viewState.value.message?.toMutableList()
        totalMessage?.add(request.messages[0])
        _viewState.update {

            it.copy(
                isLoading = true,
                message = totalMessage
            )
        }

        viewModelScope.launch {
            repository.sendRequest(request).collect { res ->
                var totalMessage = _viewState.value.message?.toMutableList()
                totalMessage?.add(res!!.choices[0].message)
                _viewState.update {
                    it.copy(
                        isLoading = false,
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
            repository.addChat(Chat(0, title, messages!!))
        }
    }

    fun getChat(id: Int) {
        viewModelScope.launch {
            repository.getChat(id).collectLatest {
                Log.e("chat", "$it")
            }
        }
    }

    /*fun getMessages() {
        viewModelScope.launch {
            repository.getMessages().collect { mes ->
                _viewState.update {
                    val myList = mes as MutableList
                    if (myList.last().role == "user" && viewState.value.isLoading == true){
                        myList.add(RoomMessage(0, "loading", "loading"))
                    }


                    it.copy(
                        message = myList
                    )

                }
            }
        }
    }*/

    /* fun addMessage(roomMessage: RoomMessage) {
         viewModelScope.launch {
             repository.addMessage(roomMessage)
         }
     }*/


}

data class HomePageViewState(
    val isLoading: Boolean? = false,
    val message: List<CustomMessage>? = mutableListOf(
        CustomMessage(
            "assistant",
            "How can I assist you?"
        )
    )
)
