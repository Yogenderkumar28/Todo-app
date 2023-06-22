package com.example.todoapp.util

sealed class UiEvent {
    object popBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
}
