package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date


@Entity(tableName = "todoTable")
data class Todo(
    val title: String,
//    val lastUpdated: LocalDateTime,
    val description: String,
    val isDone: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int?
)
