package com.example.todoapp.viewmodel

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepositoryImpl
import com.example.todoapp.util.Routes
import com.example.todoapp.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoRepositoryImpl: TodoRepositoryImpl
) : ViewModel() {

    val todos: LiveData<List<Todo>> = todoRepositoryImpl.getAllTodo()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

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
                    deletedTodo = event.todo
                    todoRepositoryImpl.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo Deleted",
                        action = "Undo"
                    ))

                }
            }
            is TodoListEvent.OnUndoTodoClick -> {
                deletedTodo?.let {todo ->
                    viewModelScope.launch {
                        todoRepositoryImpl.insertTodo(todo)
                    }
                }

            }
            is TodoListEvent.onDoneChange -> {
                viewModelScope.launch {
                    todoRepositoryImpl.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}



