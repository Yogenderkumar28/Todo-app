package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepositoryImpl
import com.example.todoapp.ui.add_edit_todo.AddEditTodoScreen
import com.example.todoapp.ui.splash_screen.SplashScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoscreen.TodoListView
import com.example.todoapp.util.Routes
import com.example.todoapp.viewmodel.AddEditTodoViewModel
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.ViewModelFactory

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private lateinit var todoDatabase: TodoDatabase
    private lateinit var todoRepository: TodoRepositoryImpl
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var addEditTodoViewModel: AddEditTodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoDatabase = TodoDatabase.getInstance(this)
        todoRepository = TodoRepositoryImpl(todoDatabase)
        viewModelFactory = ViewModelFactory(todoRepository)
        todoViewModel = ViewModelProvider(this, viewModelFactory)[TodoViewModel::class.java]
        addEditTodoViewModel = ViewModelProvider(this, viewModelFactory)[AddEditTodoViewModel::class.java]
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
//                    composable(
//                        Routes.TODO_LIST
//                    ) {
//                        TodoListView(
//                            viewModel = todoViewModel,
//                            onNavigate = { navController.navigate(it.route)}
//                        )
//                    }
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
                            onPopBackStack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
