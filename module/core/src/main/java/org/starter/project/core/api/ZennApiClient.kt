package org.starter.project.core.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

interface ZennApiClient {
    val retrofit: Retrofit
}

@Singleton
class ZennApiClientImpl @Inject constructor() : ZennApiClient {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val json = Json { ignoreUnknownKeys = true }
    override val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ZennApiConfig.API_BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}
