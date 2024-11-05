package org.example.project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import auth.login.LoginScreen
import auth.login.LoginViewModel
import auth.signup.SignUpScreen
import auth.signup.SignUpViewModel
import database.TaskDao
import home.HomePage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import theme.TaskAppTheme

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    TaskAppTheme {
        KoinContext {
            val loginViewModel = koinViewModel<LoginViewModel>()
            val signUpViewModel = koinViewModel<SignUpViewModel>()
            val taskDao = koinInject<TaskDao>()


            val navController = rememberNavController()

            NavHost(navController, startDestination = "signup") {

                composable("signup") {
                    SignUpScreen(
                        signUpViewModel.uiState,
                        //On Username changed
                        {},
                        //On Email changed
                        {},
                        //On Password changed
                        {},
                        //On Signup Button Clicked
                        {
                            navController.navigate("login")
                        }
                    )
                }

                composable("login") {
                    LoginScreen(
                        loginViewModel.uiState,
                        //On Email changed
                        {},
                        //On Password changed
                        {},
                        //On Login Button Clicked
                        {
                            navController.navigate("home")
                        }
                    )
                }

                composable("home") {
                    HomePage(taskDao = taskDao)
                }
            }
        }
    }
}