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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import composetaskapp.composeapp.generated.resources.no_tasks
import data.model.Task
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
    val task = homeViewModel.tasks.value

    Logger.w("LogTASKs") { "Tasks list updated in HOMESCREEN $task" }

    if (homeViewModel.shouldShowDialog.value) {
        AddNewTask(
            homeViewModel,
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


    if(task.isEmpty())
        Text(stringResource(Res.string.no_tasks),
            style = MaterialTheme.typography.h6)
    else
    LazyColumn(
        modifier = Modifier.padding(0.dp, 60.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(task) { task ->  // Use task.id for proper recomposition
            ListItem(
                homeViewModel,
                task.id,
                task,
                onDetailView = { }
            )
        }
    }
}
