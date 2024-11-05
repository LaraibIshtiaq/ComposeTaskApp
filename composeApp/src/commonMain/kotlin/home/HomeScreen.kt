package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomePage(){
    var showContent by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End) {
        AppBarUi()
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(20.dp)) {
            BodyContent()
        }
    }
}


@Composable
fun BodyContent() {
    var shouldShowDialog = remember { mutableStateOf(false) } // 1
    val coroutineScope = rememberCoroutineScope() // Remember the coroutine scope

//    val tasks by taskDao.getAllTasks().collectAsState(initial = emptyList())


    if (shouldShowDialog.value) {
        AddNewTask(shouldShowDialog = shouldShowDialog,
            { taskName, taskDescription ->
                coroutineScope.launch {
//                    taskDao.upsertTask(Task(0,taskName, taskDescription))
                }
            },
            stringResource(Res.string.add_task)
        )
    }

    //Add tasks Button
    Button(
        onClick = {
            shouldShowDialog.value = true
        },
    ) {
        Text(stringResource(Res.string.add_task),
            style = MaterialTheme.typography.button)
    }
//
//    if(tasks.isEmpty())
//    ///If tasks list is empty then show No Tasks present
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(stringResource(Res.string.no_tasks), style = MaterialTheme.typography.h3)
//        }
//    ///Else, Show Tasks in column
//    else
//        LazyColumn(modifier = Modifier
//            .padding(0.dp, 60.dp, 0.dp, 0.dp),
//            verticalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//            items(tasks) {task ->
//                ListItem(
//                    tasks.indexOf(task) + 1,
//                    task.title,
//                    task.description,
//                    contentDescription = task.description,
//                    //On Edit
//                    {name, description ->
//                        coroutineScope.launch {
//                            taskDao.upsertTask(Task(task.id, name, description ))
//                        }
//                    },
//                    //On Delete
//                    {
//                        coroutineScope.launch {
//                            taskDao.deleteTask(task)
//                        }
//                    },
//                    //On Detail View
//                    {
//
//                    }
//                )
//            }
//        }
}