package com.example.todoapp.ui.todoscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.Todo
import com.example.todoapp.viewmodel.TodoListEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ToDoItem(
    toDo: Todo,
    modifier: Modifier = Modifier,
    onEvent: (TodoListEvent) -> Unit
) {

    ElevatedCard(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            8.dp
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            Color(0xFFEFB8C8)
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .weight(2.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(2.5f),
                        text = toDo.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(0.5f),
                        onClick = {
                            onEvent(TodoListEvent.onDeleteTodoClick(toDo))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete")
                    }
                }
//                toDo.description?.let {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(text = it)
//                }
                Text(
                    text = "Last Updated: ${formatDate(toDo.lastUpdated)}",
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            Checkbox(
                modifier = Modifier
                    .weight(0.5f),
                checked = toDo.isDone,
                onCheckedChange = { isChecked ->
                    onEvent(TodoListEvent.onDoneChange(toDo, isChecked))
                },
                colors = CheckboxDefaults.colors(
                    Color(0xff7FFFD4)
                )
            )
        }
    }
}

private fun formatDate(timeStamp: Long): String{
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timeStamp))
}