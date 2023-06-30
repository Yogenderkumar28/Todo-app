package com.example.todoapp.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.util.UiEvent
import com.example.todoapp.viewmodel.AddEditTodoEvent
import com.example.todoapp.viewmodel.AddEditTodoViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalMaterial3Api
@Composable
fun AddEditTodoScreen(
   onPopBackStack: () -> Unit,
   viewModel: AddEditTodoViewModel
) {
   val snackbarHostState = remember { SnackbarHostState() }
   val currentDate = LocalDateTime.now()

   LaunchedEffect(key1 = true) {
      viewModel.uiEvent.collect {event ->
         when(event) {
            is UiEvent.popBackStack -> onPopBackStack()
            is UiEvent.ShowSnackBar -> {
               snackbarHostState.showSnackbar(
                  message = event.message,
                  actionLabel = event.action,
                  duration = SnackbarDuration.Short
               )
            }
            else -> Unit
         }
      }
   }
   Scaffold(
      snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
               viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }
         ) {
            Icon(
               imageVector = Icons.Default.Check,
               contentDescription = "Save"
            )
         }
      }
   ) {
      Column(
         modifier = Modifier.padding(it)
      ) {
         Text(
                text = "Last Updated: ${currentDate.format(DateTimeFormatter.ISO_DATE)}",
                modifier = Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp, top = 18.dp)
         )
         Spacer(modifier = Modifier.height(8.dp))
         TextField(
            value = viewModel.title,
            onValueChange = {
               viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
            },
            placeholder = {
               Text(text = "Title")
            },
            modifier = Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp, top = 18.dp)
         )
         Spacer(modifier = Modifier.height(8.dp))
         TextField(
            value = viewModel.description,
            onValueChange = {
               viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
            },
            placeholder = {
               Text(text = "Description")
            },
            modifier =  Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp, top = 18.dp).fillMaxSize(),
            singleLine = false,
            maxLines = 7
         )
      }
   }
}