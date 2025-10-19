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
    data class OnTapArticle(val article: Article) : HomeScreenEvent
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
                val article = event.article
                appRouter.navigate(AppRoute.Detail(AppRoute.Detail.NavArgs(article.id, article.title)))
            }

            else -> {
                /* no-op */
            }
        }
    }
}
