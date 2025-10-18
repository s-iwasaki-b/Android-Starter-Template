package org.starter.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.starter.project.ui.route.AppRoute
import org.starter.project.ui.route.AppRouter

internal class AppRouterImpl(
    internal val backStack: NavBackStack<AppRoute>
) : AppRouter {
    override fun navigate(route: AppRoute) {
        backStack.add(route)
    }

    override fun back() {
        backStack.removeLastOrNull()
    }
}

@Composable
internal fun rememberAppRouter(
    backStack: NavBackStack<NavKey>
) = remember(backStack) {
    @Suppress("UNCHECKED_CAST")
    AppRouterImpl(backStack as NavBackStack<AppRoute>)
}
