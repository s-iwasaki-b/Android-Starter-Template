package org.starter.project.data.zenn.repository

import kotlinx.coroutines.flow.last
import org.starter.project.base.data.model.zenn.Articles
import org.starter.project.data.repository.ZennRepository
import org.starter.project.data.zenn.converter.ArticlesConverter
import org.starter.project.data.zenn.datasource.api.ZennApi
import org.starter.project.data.zenn.datasource.preferences.ZennPreferences
import javax.inject.Inject

class ZennRepositoryImpl @Inject constructor(
    private val zennApi: ZennApi,
    private val zennPreferences: ZennPreferences
) : ZennRepository {
    override suspend fun fetchArticles(
        userName: String?,
        publicationName: String?,
        order: String?,
        page: Int?
    ): Articles {
        return ArticlesConverter(zennApi.fetchArticles(userName, publicationName, order, page))
    }

    override suspend fun getLastKeyword(): String = zennPreferences.lastKeyword.last()

    override suspend fun updateLastKeyword(keyword: String) {
        zennPreferences.updateLastKeyword(keyword)
    }
}
