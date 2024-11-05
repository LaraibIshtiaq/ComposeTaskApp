package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin

fun main() = application {
    ///Initializing Koin
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ComposeTaskApp",
    ) {
        App()
    }
}