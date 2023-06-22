package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.add_edit_todo.AddEditTodoScreen
import com.example.todoapp.ui.splash_screen.SplashScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoscreen.ToDoList
import com.example.todoapp.util.Routes
import com.example.todoapp.viewmodel.AddEditTodoViewModel
import com.example.todoapp.viewmodel.TodoViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    val todoViewModel: TodoViewModel by viewModels()
    val addEditTodoViewModel: AddEditTodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
               val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.SPLASH_SCREEN
                ) {
                    composable(
                        Routes.SPLASH_SCREEN
                    ) {
                        SplashScreen(navController = navController)
                    }
                    composable(
                        Routes.TODO_LIST
                    ) {
                        ToDoList(
                            viewModel = todoViewModel,
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_LIST + "?todoID={todoId}",
                        arguments = listOf(
                            navArgument(name = "todoID") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditTodoScreen(
                            viewModel = addEditTodoViewModel,
                            onPopBackStack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}
