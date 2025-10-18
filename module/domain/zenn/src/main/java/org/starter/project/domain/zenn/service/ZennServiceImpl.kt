package org.starter.project.domain.zenn.service

import androidx.annotation.OpenForTesting
import org.starter.project.data.repository.ZennRepository
import org.starter.project.domain.service.ResultHandler
import org.starter.project.domain.service.ZennService
import javax.inject.Inject

@OpenForTesting
open class ZennServiceImpl @Inject constructor(
    private val resultHandler: ResultHandler,
    private val zennRepository: ZennRepository
) : ZennService {
    override suspend fun fetchArticles(
        keyword: String,
        nextPage: Int?
    ) = resultHandler.async {
        zennRepository.updateLastKeyword(keyword)

        val publicationArticles = zennRepository.fetchArticles(
            publicationName = keyword,
            page = nextPage
        )

        return@async if (publicationArticles.articles.isEmpty()) {
            zennRepository.fetchArticles(
                userName = keyword,
                page = nextPage
            )
        } else {
            publicationArticles
        }
    }

    override suspend fun getLastKeyword() = resultHandler.async {
        zennRepository.getLastKeyword()
    }
}
