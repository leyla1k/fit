package com.example.todolist.a

sealed class LatestNewsUiState {
    data class Success(val news: List<Int>): LatestNewsUiState()
    data class Error(val exception: Throwable): LatestNewsUiState()
}