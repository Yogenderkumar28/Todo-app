package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TodoRepositoryImpl
import java.lang.Exception


class ViewModelFactory(
    private val repositoryImpl: TodoRepositoryImpl
) :ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(repositoryImpl::class.java)
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
        return super.create(modelClass)
    }
}