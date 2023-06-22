package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(toDo: Todo)

    @Delete
    suspend fun deleteTodo(toDo: Todo)

    @Query("SELECT * FROM todoTable WHERE id= :id")
    suspend fun getTodoById(id: Int): Todo?

    @Query("SELECT * FROM todoTable")
    fun getTodo(): Flow<List<Todo>>
}