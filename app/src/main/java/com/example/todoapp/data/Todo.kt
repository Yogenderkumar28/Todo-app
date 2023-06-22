package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todoTable")
data class Todo(
    val title: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val description: String?,
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int?
)
