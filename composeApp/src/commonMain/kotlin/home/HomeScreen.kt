package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import composetaskapp.composeapp.generated.resources.no_tasks
import data.model.Task
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomePage(homeViewModel: HomeViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        AppBarUi(homeViewModel = homeViewModel)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(20.dp)
        ) {
            BodyContent(homeViewModel)
        }
    }
}

@Composable
fun BodyContent(homeViewModel: HomeViewModel) {
    val tasks = homeViewModel.tasks.value

    if (homeViewModel.isAddTaskDialogVisible.value) {
        AddNewTask(homeViewModel)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddTaskButton { homeViewModel.showAddTaskDialog() }

        if (tasks.isEmpty()) {
            NoTasksMessage()
        } else {
            TaskList(tasks, homeViewModel)
        }
    }
}

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(stringResource(Res.string.add_task), style = MaterialTheme.typography.button)
    }
}

@Composable
fun NoTasksMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(Res.string.no_tasks), style = MaterialTheme.typography.h4)
    }
}

@Composable
fun TaskList(tasks: List<Task>, homeViewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier.padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(tasks) { index, task ->
            ListItem(homeViewModel, index + 1, task)
        }
    }
}
