package home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.tasks_app
import org.jetbrains.compose.resources.stringResource


@Composable
fun AppBarUi(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(color = MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center

    ){
        Text(
            stringResource(Res.string.tasks_app),
            style = MaterialTheme.typography.h5
        )
    }
}