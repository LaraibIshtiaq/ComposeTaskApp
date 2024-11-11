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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import composetaskapp.composeapp.generated.resources.cancel
import composetaskapp.composeapp.generated.resources.enter_description_here
import data.model.Priority
import data.model.Task
import org.jetbrains.compose.resources.stringResource
import theme.SmallSpacing

@Composable
fun AddNewTask(
    homeViewModel: HomeViewModel,
) {
    val taskName = remember { mutableStateOf("") }
    val titleHint = stringResource(Res.string.add_task)
    val taskDescription = remember { mutableStateOf("") }
    val descriptionHint = stringResource(Res.string.enter_description_here)
    val taskPriority = remember { mutableStateOf(Priority.Low) }
    val textLength = remember { mutableStateOf(0) }

    if(homeViewModel.shouldShowDialog.value){
        AlertDialog(
            title = {
                Text(text =  stringResource(Res.string.add_task))
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
                homeViewModel.shouldShowDialog.value = false
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        homeViewModel.upsertTask(
                            Task(
                                0,
                                taskName.value,
                                taskDescription.value,
                                taskPriority.value)
                        )
                        homeViewModel.shouldShowDialog.value = false
                              },
                    ) {
                    Text(stringResource(Res.string.add_task))
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        homeViewModel.shouldShowDialog.value = false
                    }
                ) {
                    Text(text = stringResource(Res.string.cancel))
                }
            }
        )
    }
}