package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.util.Routes
import com.example.todoapp.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repositoryImpl: TodoRepository
) : ViewModel() {

    val todos = repositoryImpl.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.onTodoClick -> {
                viewModelScope.launch {
                    UiEvent.Navigate(Routes.ADD_EDIT_LIST + "?todoId=${event.todo.id}")
                }
            }
            is TodoListEvent.onAddTodoClick -> {
                viewModelScope.launch {
                    UiEvent.Navigate(Routes.ADD_EDIT_LIST)
                }
            }
            is TodoListEvent.onDeleteTodoClick -> {
                viewModelScope.launch {
                    repositoryImpl.deleteTodo(event.todo)
                }
            }
            is TodoListEvent.onDoneChange -> {
                viewModelScope.launch {
                    repositoryImpl.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }
}

