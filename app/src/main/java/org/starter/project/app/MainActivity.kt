package org.starter.project.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation3.runtime.rememberNavBackStack
import dagger.hilt.android.AndroidEntryPoint
import org.starter.project.navigation.AppNavDisplay
import org.starter.project.ui.route.AppRoute
import org.starter.project.navigation.rememberAppRouter
import org.starter.project.ui.design.system.theme.SystemTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val backStack = rememberNavBackStack(AppRoute.Home)
            val appRouter = rememberAppRouter(backStack)
            SystemTheme {
                AppNavDisplay(appRouter)
            }
        }
    }
}
