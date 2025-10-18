package org.starter.project.feature.home.component.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.starter.project.base.data.model.zenn.Article
import org.starter.project.base.data.model.zenn.Articles

internal class ArticlesPagingSource(
    private val onRefresh: () -> Unit = {},
    private val onLoadedFirstPage: () -> Unit = {},
    private val fetcher: suspend (key: Int?) -> Articles?,
) : PagingSource<Int, Article>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            params.key ?: onRefresh()
            val currentKey = params.key
            val data = fetcher(currentKey)
            val nextKey = data?.nextPage
            currentKey ?: onLoadedFirstPage()
            LoadResult.Page(
                data = data?.articles.orEmpty(),
                prevKey = currentKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
