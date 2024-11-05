package org.example.project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import auth.login.LoginScreen
import auth.signup.SignUpScreen
import home.HomePage
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.TaskAppTheme

@Composable
@Preview
fun App() {
    TaskAppTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "signup") {

            composable("signup") {
                SignUpScreen(
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
                HomePage()
            }
        }
    }
}