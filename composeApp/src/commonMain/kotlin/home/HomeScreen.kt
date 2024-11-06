package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import composetaskapp.composeapp.generated.resources.no_tasks
import model.Task
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomePage(homeViewModel: HomeViewModel){
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End) {
        AppBarUi()
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(20.dp)) {
            BodyContent(homeViewModel= homeViewModel)
        }
    }
}


@Composable
fun BodyContent(homeViewModel: HomeViewModel) {
    val tasks by homeViewModel.tasks.collectAsState(initial = emptyList())


    if (homeViewModel.shouldShowDialog.value) {
        AddNewTask(shouldShowDialog = homeViewModel.shouldShowDialog,
            { taskName, taskDescription ->
                homeViewModel.upsertTask(Task(0,taskName, taskDescription))
            },
            stringResource(Res.string.add_task)
        )
    }

    //Add tasks Button
    Button(
        onClick = {
            homeViewModel.showAddTaskDialog()
        },
    ) {
        Text(stringResource(Res.string.add_task),
            style = MaterialTheme.typography.button)
    }

    if(tasks.isEmpty())
    ///If tasks list is empty then show No Tasks present
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(Res.string.no_tasks), style = MaterialTheme.typography.h4)
        }
    ///Else, Show Tasks in column
    else
        LazyColumn(modifier = Modifier
            .padding(0.dp, 60.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tasks) {task ->
                ListItem(
                    tasks.indexOf(task) + 1,
                    task.title,
                    task.description,
                    onEdit = { name, description ->
                        homeViewModel.upsertTask(Task(task.id, name, description ))
                    },
                    onDelete = {
                        homeViewModel.deleteTask(task)
                    },
                    onDetailView = {

                    }
                )
            }
        }
}