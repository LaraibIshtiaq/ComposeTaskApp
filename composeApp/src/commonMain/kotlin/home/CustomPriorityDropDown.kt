package home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.ic_drop_down
import model.Priority
import org.jetbrains.compose.resources.painterResource
import theme.ExtraLargeSpacing


@Composable
fun CustomPriorityDropDown(homeViewModel: HomeViewModel, taskPriority: MutableState<Priority>) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(homeViewModel.priorities.indexOf(taskPriority.value))
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = homeViewModel.priorities[itemPosition.value].toString(),
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(
                    modifier = Modifier
                        .width(ExtraLargeSpacing)
                )
                Image(
                    painter = painterResource(Res.drawable.ic_drop_down),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                homeViewModel.priorities.forEachIndexed { index, priority ->
                    DropdownMenuItem(content = {
                        Text(text = priority.toString(),
                            style = MaterialTheme.typography.body2)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            taskPriority.value = homeViewModel.priorities[itemPosition.value]
                        })
                }
            }
        }

    }
}