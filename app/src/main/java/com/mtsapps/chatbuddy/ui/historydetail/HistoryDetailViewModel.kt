package com.mtsapps.chatbuddy.ui.historydetail

import androidx.lifecycle.ViewModel
import com.mtsapps.chatbuddy.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel@Inject constructor (private val repository: Repository)  : ViewModel() {
}