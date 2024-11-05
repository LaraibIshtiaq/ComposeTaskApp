package components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.ic_not_visible
import composetaskapp.composeapp.generated.resources.ic_visible
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value : String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordTextField: Boolean = false,
    isSingleLine: Boolean = true,
    hint: String
) {
    var isPasswordVisible by remember{
        mutableStateOf(false)
    }

    TextField(
        value,
        onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        textStyle = MaterialTheme.typography.h4,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        singleLine = isSingleLine,
        placeholder = {
            Text(hint)
        },
        trailingIcon = {
            if (isPasswordTextField) {
                PasswordEyeIcon(isPasswordVisible = isPasswordVisible) {
                    isPasswordVisible = !isPasswordVisible
                }
            } else {
                null
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if(isSystemInDarkTheme())
            {
                MaterialTheme.colors.surface
            }
            else{
                MaterialTheme.colors.background
            },
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Blue
        ),
        visualTransformation = if(isPasswordTextField){
            if(isPasswordVisible){
                VisualTransformation.None
            }
            else{
                PasswordVisualTransformation()
            }
        }
        else{
            VisualTransformation.None
        },
    )
}


@Composable
fun PasswordEyeIcon(
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
){
    val image = if(isPasswordVisible){
        painterResource(Res.drawable.ic_visible)
    }
    else{
        painterResource(Res.drawable.ic_not_visible)
    }

    IconButton(onClick = onPasswordVisibilityToggle){
        Icon(painter = image, contentDescription = null)
    }
}