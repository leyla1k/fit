package com.example.todolist.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.a.LatestNewsUiState
import com.example.todolist.a.Left
import com.example.todolist.a.Right
import com.example.todolist.a.safeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<LatestNewsUiState> =
        MutableStateFlow(LatestNewsUiState.Success(emptyList()))
    val uiState: StateFlow<LatestNewsUiState> = _uiState

    init {

    }
}