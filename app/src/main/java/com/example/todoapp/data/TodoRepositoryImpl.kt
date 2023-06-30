package com.example.todoapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val todoDatabase: TodoDatabase
) {

    fun getAllTodo(): LiveData<List<Todo>> = todoDatabase.todoDao().getTodo()

    suspend fun insertTodo(todo: Todo) {
       todoDatabase.todoDao().insertTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
       todoDatabase.todoDao().deleteTodo(todo)
    }

    suspend fun getTodoById(id: Int): Todo? {
        return todoDatabase.todoDao().getTodoById(id)
    }

}