package org.example.project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import auth.login.LoginScreen
import auth.login.LoginViewModel
import auth.signup.SignUpScreen
import auth.signup.SignUpViewModel
import home.HomePage
import home.HomeViewModel
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


            NavHost(navController, startDestination = "signup") {
                composable("signup") {
                    SignUpScreen(
                        signUpViewModel,
                        //On Signup Button Clicked
                        { navController.navigate("home") },
                        //On Already Have An Account
                        { navController.navigate("login") }
                    )

                }

                composable("login") {
                    LoginScreen(
                        loginViewModel,
                        //On Login Button Clicked
                        { navController.navigate("home") },

                        //On Having No Account
                        { navController.navigate("signup") }
                    )
                }

                composable("home") {
                    HomePage(homeViewModel)
                }
            }
        }
    }
}