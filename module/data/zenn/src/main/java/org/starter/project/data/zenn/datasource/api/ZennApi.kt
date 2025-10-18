package org.starter.project.data.zenn.datasource.api

import retrofit2.http.GET
import retrofit2.http.Query
import org.starter.project.data.zenn.datasource.api.response.ArticlesResponse

interface ZennApi {
    @GET("api/articles")
    suspend fun fetchArticles(
        @Query("username") userName: String? = null,
        @Query("publication_name") publicationName: String? = null,
        @Query("order") order: String? = null,
        @Query("page") page: Int? = null
    ): ArticlesResponse
}
