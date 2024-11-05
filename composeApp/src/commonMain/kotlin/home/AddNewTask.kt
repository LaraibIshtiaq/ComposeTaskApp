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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.add_task
import composetaskapp.composeapp.generated.resources.cancel
import composetaskapp.composeapp.generated.resources.enter_description_here
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddNewTask(
    shouldShowDialog: MutableState<Boolean>,
    onConfirmation: (value: String, value1: String) -> Unit,
    dialogTitle: String,
) {
    val taskName = remember { mutableStateOf("") }
    val titleHint = stringResource(Res.string.add_task)
    val taskDescription = remember { mutableStateOf("") }
    val descriptionHint = stringResource(Res.string.enter_description_here)
    val textLength = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope() // Remember the coroutine scope

    if(shouldShowDialog.value){
        AlertDialog(
            title = {
                Text(text = dialogTitle)
            },

            text = {
                Column(
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
                }

            },


            onDismissRequest = {
                shouldShowDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            // Calling suspend function inside coroutine
                            onConfirmation(taskName.value, taskDescription.value)
                            shouldShowDialog.value = false
                        }
                    }
                ) {
                    Text(stringResource(Res.string.add_task))
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