package home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.ic_delete
import composetaskapp.composeapp.generated.resources.ic_edit
import data.model.Task
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItem(
    homeViewModel: HomeViewModel,
    itemNumber: Int,
    task: Task,
    onDetailView: () -> Unit){

    var shouldShowUpdateDialog = remember { mutableStateOf(false) }

    if (shouldShowUpdateDialog.value) {
        UpdateTask(
            homeViewModel,
            task,
            shouldShowDialog = shouldShowUpdateDialog,
        )
    }

    Card(
        onClick = {
            onDetailView()
        },
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    "$itemNumber ${task.title}",
                    style = MaterialTheme.typography.body1,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Text(
                    task.description,
                    style = MaterialTheme.typography.body2,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column {
                Text(
                    "${task.priority}",
                    style = MaterialTheme.typography.body1,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = "Delete icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                homeViewModel.deleteTask(task)
                            },
                        tint = MaterialTheme.colors.onSurface
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = "Edit icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                shouldShowUpdateDialog.value = true
                            },
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }

}