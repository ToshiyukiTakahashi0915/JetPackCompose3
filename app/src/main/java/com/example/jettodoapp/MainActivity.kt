package com.example.jettodoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jettodoapp.components.EditDailog
import com.example.jettodoapp.components.TaskList
import com.example.jettodoapp.ui.theme.JetTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()

                }
            }
        }
    }
}

@Composable
fun MainContent(viewMOdel: MainViewModel = hiltViewModel()){
        if (viewMOdel.isShowDialog){
    EditDailog()
    }


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewMOdel.isShowDialog = true }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "新規作成")
        }
    }) {
        val tasks by viewMOdel.tasks.collectAsState(initial = emptyList())

        TaskList(
            tasks = tasks,
            onClickRow = {
                viewMOdel.setEditingTask(it)
                         viewMOdel.isShowDialog = true
            },
            onClickDelete = { viewMOdel.deleteTask(it)},
        )
    }
}