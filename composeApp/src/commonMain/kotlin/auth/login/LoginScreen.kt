package auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import components.CustomTextField
import composetaskapp.composeapp.generated.resources.Res
import composetaskapp.composeapp.generated.resources.enter_email
import composetaskapp.composeapp.generated.resources.enter_password
import composetaskapp.composeapp.generated.resources.login
import composetaskapp.composeapp.generated.resources.signup
import io.ktor.websocket.Frame
import navigation.NavRoutes
import org.jetbrains.compose.resources.stringResource
import theme.ButtonHeight
import theme.ExtraLargeSpacing
import theme.LargeSpacing
import theme.MediumSpacing
import theme.SmallSpacing

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Surface(modifier = Modifier
        .fillMaxSize()) {

        val state = loginViewModel.uiState.collectAsState()


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
        ){
            when (val registerState = state.value) {
                LoginState.Loading -> {
                    CircularProgressIndicator()
                    Frame.Text("Loading")
                }

                is LoginState.Error -> {
                    Text(registerState.message)
                    Button(onClick = {
                        loginViewModel.retry()
                    }) {
                        Text("Retry")
                    }
                }

                LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(NavRoutes.Home.route){
                            popUpTo(NavRoutes.Login.route){
                                inclusive = true
                            }
                        }
                    }
                }

                LoginState.Nothing -> {
                    Text(
                        stringResource(Res.string.login),
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
                        value = email,
                        onValueChange = { newValue -> email = newValue },
                        keyboardType = KeyboardType.Email,
                        hint = stringResource(Res.string.enter_email)
                    )

                    CustomTextField(
                        value = password,
                        onValueChange = { newValue -> password = newValue },
                        keyboardType = KeyboardType.Password,
                        isPasswordTextField = true,
                        hint = stringResource(Res.string.enter_password)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "already have an account?",
                            style = MaterialTheme.typography.caption
                        )
                        TextButton(
                            onClick = {

                            }
                        ) {
                            Text(
                                stringResource(Res.string.signup),
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }


                    Button(
                        enabled = email.isNotEmpty() && password.isNotEmpty(),
                        onClick = {
                            loginViewModel.login(email = email, password = password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ButtonHeight)
                            .background(
                                color = if(isSystemInDarkTheme()){
                                    MaterialTheme.colors.background
                                }
                                else{
                                    MaterialTheme.colors.background
                                }
                            ),
                        shape = MaterialTheme.shapes.medium,
                    ){
                        Text(text = stringResource(Res.string.login),
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}