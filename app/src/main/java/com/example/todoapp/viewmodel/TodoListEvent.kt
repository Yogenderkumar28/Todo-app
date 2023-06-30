package com.example.todoapp.viewmodel

import com.example.todoapp.data.Todo

sealed class TodoListEvent {
    data class onDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class onDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
    data class onTodoClick(val todo: Todo): TodoListEvent()

    object OnUndoTodoClick: TodoListEvent()
    object onAddTodoClick: TodoListEvent()
}
