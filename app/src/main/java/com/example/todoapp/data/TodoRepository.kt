package com.example.todoapp.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }

    fun getTodos(): Flow<List<Todo>> {
        return todoDao.getTodo()
    }


}