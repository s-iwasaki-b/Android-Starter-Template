package org.starter.project.ui.route

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Home : AppRoute

    @Serializable
    data class Detail(val navArgs: NavArgs) : AppRoute {
        @Serializable
        data class NavArgs(val id: Int)
    }
}

interface AppRouter {
    fun navigate(route: AppRoute)
    fun back()
}
