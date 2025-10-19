package org.starter.project.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.starter.project.feature.detail.DetailScreen
import org.starter.project.feature.detail.DetailScreenViewModelFactory
import org.starter.project.feature.home.HomeScreen
import org.starter.project.ui.route.AppRoute

@Composable
internal fun AppNavDisplay(
    appRouter: AppRouterImpl
) {
    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        backStack = appRouter.backStack
    ) { key ->
        when (key) {
            is AppRoute.Home -> NavEntry(key) {
                HomeScreen(viewModel = hiltViewModel(), appRouter = appRouter)
            }

            is AppRoute.Detail -> NavEntry(key) {
                val viewModel = hiltViewModel { factory: DetailScreenViewModelFactory ->
                    factory.create(key.navArgs)
                }
                DetailScreen(viewModel = viewModel, appRouter = appRouter)
            }
        }
    }
}
