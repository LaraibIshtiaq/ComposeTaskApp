package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import auth.login.LoginScreen
import auth.login.LoginViewModel
import auth.signup.SignUpScreen
import auth.signup.SignUpViewModel
import home.HomePage
import home.HomeViewModel
import navigation.NavRoutes
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
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
            val homeViewModel = koinViewModel<HomeViewModel>()
            val navController = rememberNavController()


            NavHost(navController, startDestination = NavRoutes.Signup.route) {
                composable(NavRoutes.Signup.route) {
                    SignUpScreen(
                        navController,
                        signUpViewModel,
                    )
                }

                composable(NavRoutes.Login.route) {
                    LoginScreen(
                        navController,
                        loginViewModel,
                    )
                }

                composable(NavRoutes.Home.route) {
                    HomePage(homeViewModel)
                }

                composable(NavRoutes.Loading.route){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text("Loading...")
                    }
                }
            }
        }
    }
}