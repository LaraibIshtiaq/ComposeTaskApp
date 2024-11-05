package auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import components.CustomTextField
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.enter_email
import composetaskapp.composeapp.generated.resources.enter_password
import composetaskapp.composeapp.generated.resources.enter_user_name
import composetaskapp.composeapp.generated.resources.signup
import org.jetbrains.compose.resources.stringResource
import theme.ButtonHeight
import theme.ExtraLargeSpacing
import theme.LargeSpacing
import theme.MediumSpacing
import theme.SmallSpacing

@Composable
fun SignUpScreen(
    uiState : SignUpUiState,
    onUserNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignupButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = if(isSystemInDarkTheme()){
                    MaterialTheme.colors.background
                }
                else{
                    MaterialTheme.colors.surface
                }
            )
            .padding(
                top = ExtraLargeSpacing + LargeSpacing,
                start = LargeSpacing + MediumSpacing,
                end = LargeSpacing + MediumSpacing,
                bottom = LargeSpacing
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {

        Text(
            stringResource(Res.string.signup),
            style = MaterialTheme.typography.h4,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colors.onSurface
            }
            else{
                MaterialTheme.colors.onBackground
            }
        )

        Box(
            modifier = Modifier
                .height(SmallSpacing)
        )

        CustomTextField(
            value = uiState.username,
            onValueChange = onUserNameChange,
            keyboardType = KeyboardType.Text,
            hint = stringResource(Res.string.enter_user_name)
        )

        CustomTextField(
            value = uiState.email,
            onValueChange = onEmailChange,
            keyboardType = KeyboardType.Email,
            hint = stringResource(Res.string.enter_email)
        )

        CustomTextField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            keyboardType = KeyboardType.Password,
            isPasswordTextField = true,
            hint = stringResource(Res.string.enter_password)
        )

        Button(
            onClick = {
                onSignupButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight)
                .background(
                    color = if(isSystemInDarkTheme()){
                        MaterialTheme.colors.surface
                    }
                    else{
                        MaterialTheme.colors.background
                    }
                ),
            shape = MaterialTheme.shapes.medium,
        ){
            Text(text = stringResource(Res.string.signup),
                style = MaterialTheme.typography.button,
            )
        }
    }
}