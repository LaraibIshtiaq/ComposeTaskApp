package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.cancel
import composetaskapp.composeapp.generated.resources.enter_description_here
import composetaskapp.composeapp.generated.resources.enter_task_title_here
import composetaskapp.composeapp.generated.resources.update_task
import model.Task
import org.jetbrains.compose.resources.stringResource
import theme.SmallSpacing

@Composable
fun UpdateTask(
    homeViewModel: HomeViewModel,
    task: Task,
    shouldShowDialog: MutableState<Boolean>,
) {
    val taskName = remember { mutableStateOf(task.title) }
    val titleHint = stringResource(Res.string.enter_task_title_here)
    val taskDescription = remember { mutableStateOf(task.description) }
    val descriptionHint = stringResource(Res.string.enter_description_here)
    val taskPriority = remember { mutableStateOf(task.priority) }
    val textLength = remember { mutableStateOf(0) }

    if(shouldShowDialog.value){
        AlertDialog(
            title = {
                Text(text = stringResource(Res.string.update_task))
            },

            text = {
                Column(
                    modifier = Modifier
                        .padding(0.dp, SmallSpacing),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = taskName.value,
                        onValueChange = {
                            if (it.length <= 200) {
                                textLength.value = it.length
                                taskName.value = it
                            }
                        },
                        label = {
                            Text(text = titleHint)
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .sizeIn(minHeight = 120.dp)
                            .background(Color.Transparent)
                    )

                    TextField(
                        value = taskDescription.value,
                        onValueChange = {
                            if (it.length <= 200) {
                                textLength.value = it.length
                                taskDescription.value = it
                            }
                        },
                        label = {
                            Text(text = descriptionHint)
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .sizeIn(minHeight = 120.dp)
                            .background(Color.Transparent)
                    )

                    CustomPriorityDropDown(homeViewModel, taskPriority)

                }

            },


            onDismissRequest = {
                shouldShowDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        homeViewModel.upsertTask(Task(task.id, taskName.value, taskDescription.value, taskPriority.value))
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(text= stringResource(Res.string.update_task))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(text = stringResource(Res.string.cancel))
                }
            }
        )
    }
}