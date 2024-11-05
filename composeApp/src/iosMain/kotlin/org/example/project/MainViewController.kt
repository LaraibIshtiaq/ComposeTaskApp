package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import platform.UIKit.UIViewController

fun MainViewController():  UIViewController{
    return ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }
}