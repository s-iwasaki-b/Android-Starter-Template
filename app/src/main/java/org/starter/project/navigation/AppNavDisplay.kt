package org.starter.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import org.starter.project.feature.detail.DetailScreen
import org.starter.project.feature.home.HomeScreen
import org.starter.project.ui.route.AppRoute

@Composable
internal fun AppNavDisplay(
    appRouter: AppRouterImpl
) {
    NavDisplay(
        backStack = appRouter.backStack
    ) { key ->
        when (key) {
            is AppRoute.Home -> NavEntry(key) {
                HomeScreen(appRouter = appRouter)
            }

            is AppRoute.Detail -> NavEntry(key) {
                DetailScreen(appRouter = appRouter, args = key.navArgs)
            }
        }
    }
}
