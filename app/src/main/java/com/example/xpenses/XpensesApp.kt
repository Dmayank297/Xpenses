package com.example.xpenses

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xpenses.ui.screens.MainPage
import com.example.xpenses.ui.theme.XpensesTheme

@Composable
fun XpensesApp() {
    XpensesTheme(
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            NavHost(
                navController = appState.navController,
                startDestination = "main_page"
            ) {
                XpensesAppGraph(
                    appState = appState
                )
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        NavigationState(navController)
    }


fun NavGraphBuilder.XpensesAppGraph(appState: NavigationState) {
    composable(route = "main_page") {
        MainPage()
    }
}