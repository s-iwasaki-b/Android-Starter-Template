package org.starter.project.feature.home

import androidx.paging.compose.LazyPagingItems
import org.starter.project.base.data.model.zenn.Article
import org.starter.project.ui.route.AppRoute
import org.starter.project.ui.route.AppRouter
import org.starter.project.ui.shared.event.ScreenEvent

internal sealed interface HomeScreenEvent : ScreenEvent {
    data object OnTapErrorScreenAction : HomeScreenEvent
    data class OnChangeSearchKeyword(val keyword: String) : HomeScreenEvent
    data object OnTapClearSearchKeyword : HomeScreenEvent
    data object OnTapActionSearchKeyword : HomeScreenEvent
    data class OnTapArticle(val id: Int) : HomeScreenEvent
}

internal object HomeScreenEventHandler {
    operator fun invoke(
        event: ScreenEvent,
        appRouter: AppRouter,
        viewModel: HomeScreenViewModel,
        articlesPagingItems: LazyPagingItems<Article>
    ) {
        when (event) {
            HomeScreenEvent.OnTapErrorScreenAction -> {
                articlesPagingItems.refresh()
            }

            is HomeScreenEvent.OnChangeSearchKeyword -> {
                viewModel.updateSearchKeyword(event.keyword)
            }

            HomeScreenEvent.OnTapClearSearchKeyword -> {
                viewModel.updateSearchKeyword("")
                articlesPagingItems.refresh()
            }

            HomeScreenEvent.OnTapActionSearchKeyword -> {
                articlesPagingItems.refresh()
            }

            is HomeScreenEvent.OnTapArticle -> {
                appRouter.navigate(AppRoute.Detail(AppRoute.Detail.NavArgs(event.id)))
            }

            else -> {
                /* no-op */
            }
        }
    }
}
