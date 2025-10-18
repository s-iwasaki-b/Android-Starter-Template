package org.starter.project.feature.detail

import org.starter.project.ui.route.AppRouter
import org.starter.project.ui.shared.event.ScreenEvent

internal sealed interface DetailScreenEvent : ScreenEvent {
    data object OnTapBackIcon : DetailScreenEvent
}

internal object DetailScreenEventHandler {
    operator fun invoke(
        event: ScreenEvent,
        appRouter: AppRouter,
        viewModel: DetailScreenViewModel
    ) {
        when (event) {
            DetailScreenEvent.OnTapBackIcon -> {
                appRouter.back()
            }

            else -> {
                /* no-op */
            }
        }
    }
}
