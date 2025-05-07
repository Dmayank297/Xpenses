package com.example.xpenses

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable

@Stable
sealed class Screen(val title: String, val route: String) {

    sealed class BottomNavScreen(
        val bTitle: String, val bRoute: String,
        @DrawableRes val bIcon: Int = 0,
        @DrawableRes val bSelectedIcon: Int = 0,
    ) : Screen(bTitle, bRoute) {

        object MainPage : BottomNavScreen(
            bTitle = "Overview",
            bRoute = "main_page",
            bIcon = R.drawable.overview,
            bSelectedIcon = R.drawable.overview
        )

        object ExpensesList : BottomNavScreen(
            bTitle = "Expenses",
            bRoute = "expenses_list",
            bIcon = R.drawable.tax_planning,
            bSelectedIcon = R.drawable.tax_planning
        )

        object Profile : BottomNavScreen(
            bTitle = "Profile",
            bRoute = "profile",
            bIcon = R.drawable.profile,
            bSelectedIcon = R.drawable.profile
        )


    }
}

val bottomNavItems= listOf(
    Screen.BottomNavScreen.MainPage,
    Screen.BottomNavScreen.ExpensesList,
    Screen.BottomNavScreen.Profile
)