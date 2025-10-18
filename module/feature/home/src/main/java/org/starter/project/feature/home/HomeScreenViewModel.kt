package org.starter.project.feature.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.starter.project.base.data.model.zenn.Articles
import org.starter.project.base.extension.handle
import org.starter.project.domain.service.ZennService
import org.starter.project.feature.home.component.paging.ArticlesPagingSource
import org.starter.project.ui.shared.handler.ErrorScreenThrowableHandler
import org.starter.project.ui.shared.handler.IgnoreThrowableHandler
import org.starter.project.ui.shared.state.ScreenLoadingState
import org.starter.project.ui.shared.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val zennService: ZennService
) : ViewModel() {
    private val _screenState = MutableStateFlow(
        ScreenState(ScreenLoadingState.Initial(true), null)
    )
    private val _state = MutableStateFlow(
        HomeScreenState(screenState = _screenState.value)
    )
    internal val state = combine(
        _screenState,
        _state
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        _state.value
    )

    val articlesPagingFlow = Pager(
        PagingConfig(ArticlesPagingSource.PAGE_SIZE)
    ) {
        ArticlesPagingSource(
            onRefresh = ::updateScreenLoading,
            onLoadedFirstPage = ::updateScreenSuccess,
            fetcher = ::fetchArticles
        )
    }.flow.cachedIn(viewModelScope)

    @VisibleForTesting
    internal fun updateScreenLoading() {
        _screenState.update {
            it.copy(screenLoadingState = ScreenLoadingState.Loading())
        }
    }

    @VisibleForTesting
    internal fun updateScreenSuccess() {
        if (_screenState.value.screenLoadingState !is ScreenLoadingState.Failure) {
            _screenState.update {
                it.copy(screenLoadingState = ScreenLoadingState.Success())
            }
        }
    }

    @VisibleForTesting
    internal suspend fun fetchArticles(key: Int?): Articles? {
        return zennService.fetchArticles(
            keyword = _state.value.searchKeyword,
            nextPage = key
        ).handle(ErrorScreenThrowableHandler(_screenState))
    }

    fun initSearchKeyword() {
        viewModelScope.launch {
            val lastKeyword =
                zennService.getLastKeyword().handle(IgnoreThrowableHandler()).orEmpty()
            _state.update {
                it.copy(
                    searchKeyword = lastKeyword
                )
            }
        }
    }

    fun updateSearchKeyword(keyword: String) {
        _state.update {
            it.copy(
                searchKeyword = keyword
            )
        }
    }
}
