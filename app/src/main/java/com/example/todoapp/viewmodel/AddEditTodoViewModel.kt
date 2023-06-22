package com.example.todoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoRepository
import com.example.todoapp.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddEditTodoViewModel(
    private val repository: TodoRepository,  // this will contain the the implementation of dao
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentDate = formatDate(System.currentTimeMillis())

    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var lastUpdated by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    lastUpdated = currentDate
                    this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when(event) {
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description =  description,
                            lastUpdated = currentDate.toLong(),
                            id = todo?.id,
                            isDone = todo?.isDone ?: false

                        )
                    )
                }
            }
        }
    }



}

private fun formatDate(timeStamp: Long): String{
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timeStamp))
}