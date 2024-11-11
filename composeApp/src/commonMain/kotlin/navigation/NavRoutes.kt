package navigation

sealed class NavRoutes(val route: String) {
    data object Loading : NavRoutes("loading")
    data object Login : NavRoutes("login")
    data object Home : NavRoutes("home")
    data object Signup : NavRoutes("signup")
}