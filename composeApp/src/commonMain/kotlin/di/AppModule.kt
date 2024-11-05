package di

import auth.login.LoginViewModel
import auth.signup.SignUpViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
}

expect val platformModule : Module