package di

import auth.login.LoginViewModel
import auth.signup.SignUpViewModel
import data.NetworkService
import home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeViewModel)
    single{
        NetworkService(get())
    }

    single{
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("BackEndHandler: $message")
                    }
                }
            }
        }
    }
}

expect val platformModule : Module