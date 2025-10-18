package org.starter.project.data.repository

import org.starter.project.base.data.model.zenn.Articles

interface ZennRepository : Repository {
    suspend fun fetchArticles(
        userName: String? = null,
        publicationName: String? = null,
        order: String? = "latest",
        page: Int? = null
    ): Articles
    suspend fun getLastKeyword(): String
    suspend fun updateLastKeyword(keyword: String)
}
