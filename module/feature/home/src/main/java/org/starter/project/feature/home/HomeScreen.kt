package org.starter.project.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import org.starter.project.base.data.model.zenn.Article
import org.starter.project.feature.home.component.article.articleList
import org.starter.project.ui.design.system.scaffold.SystemScaffold
import org.starter.project.ui.design.system.search.SystemSearchBar
import org.starter.project.ui.design.system.theme.SystemTheme
import org.starter.project.ui.route.AppRouter
import org.starter.project.ui.shared.event.ScreenEvent

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(),
    appRouter: AppRouter
) {
    val state by viewModel.state.collectAsState()
    val articlesPagingItems = viewModel.articlesPagingFlow.collectAsLazyPagingItems()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.initSearchKeyword()
    }

    HomeScreenContent(
        state = state,
        articlesPagingItems = articlesPagingItems
    ) { event ->
        HomeScreenEventHandler(
            event = event,
            appRouter = appRouter,
            viewModel = viewModel,
            articlesPagingItems = articlesPagingItems
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeScreenState,
    articlesPagingItems: LazyPagingItems<Article>,
    dispatch: (event: ScreenEvent) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(articlesPagingItems.loadState.refresh) {
        if (articlesPagingItems.loadState.refresh is LoadState.NotLoading) {
            listState.scrollToItem(0)
        }
    }

    SystemScaffold(
        modifier = Modifier.fillMaxSize(),
        screenState = state.screenState,
        onTapErrorActionButton = {
            dispatch(HomeScreenEvent.OnTapErrorScreenAction)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            state = listState
        ) {
            stickyHeader(key = "search_bar") {
                SystemSearchBar(
                    modifier = Modifier
                        .background(SystemTheme.colors.background)
                        .padding(vertical = 16.dp),
                    value = state.searchKeyword,
                    onValueChange = { dispatch(HomeScreenEvent.OnChangeSearchKeyword(it)) },
                    onTapClear = { dispatch(HomeScreenEvent.OnTapClearSearchKeyword) },
                    onTapAction = { dispatch(HomeScreenEvent.OnTapActionSearchKeyword) }
                )
            }
            articleList(
                articlesPagingItems = articlesPagingItems,
                onClick = { dispatch(HomeScreenEvent.OnTapArticle(it)) }
            )
        }
    }
}
