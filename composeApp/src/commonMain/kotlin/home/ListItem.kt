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
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItem(itemNumber: Int,
                taskTitle: String,
                taskDescription: String,
                onEdit: (value: String, value1: String) -> Unit,
                onDelete: () -> Unit,
                onDetailView: () -> Unit){

    var shouldShowUpdateDialog = remember { mutableStateOf(false) }

    if (shouldShowUpdateDialog.value) {
        UpdateTask(
            taskTitle,
            taskDescription,
            shouldShowDialog = shouldShowUpdateDialog,
            { name, description -> onEdit(name, description)
            },
            "Update Task"
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
                .padding(16.dp), // Added padding for a better responsive look
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // Allow column to take up available space
                    .padding(end = 16.dp) // Added padding between text and icons for better spacing
            ) {
                Text(
                    "$itemNumber $taskTitle",
                    style = MaterialTheme.typography.body1,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                    maxLines = 1, // Limit to one line to handle overflow
                    overflow = TextOverflow.Ellipsis // Use ellipsis if text is too long
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp) // Adjusted spacing for better responsiveness
                )

                Text(
                    taskDescription,
                    style = MaterialTheme.typography.body2,
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        MaterialTheme.colors.onBackground
                    },
                    maxLines = 2, // Limit to two lines for better responsiveness
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier
                    .wrapContentWidth() // Make row wrap its content width
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = "Delete icon",
                    modifier = Modifier
                        .size(24.dp) // Set a responsive size
                        .clickable {
                            onDelete()
                        },
                    tint = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.width(16.dp)) // Spacing between icons

                Icon(
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = "Edit icon",
                    modifier = Modifier
                        .size(24.dp) // Set a responsive size
                        .clickable {
                            shouldShowUpdateDialog.value = true
                        },
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }

}