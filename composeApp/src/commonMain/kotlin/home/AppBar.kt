package home

import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.tasks_app
import org.jetbrains.compose.resources.stringResource


@Composable
fun AppBarUi(homeViewModel: HomeViewModel) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        title = {
            Text(
                text = stringResource(Res.string.tasks_app),
                style = MaterialTheme.typography.h5
            )
        },
        actions = {
            IconButton(onClick = { homeViewModel.loadTasks() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Tasks")
            }
        },
        modifier = Modifier.height(80.dp)
    )
}
