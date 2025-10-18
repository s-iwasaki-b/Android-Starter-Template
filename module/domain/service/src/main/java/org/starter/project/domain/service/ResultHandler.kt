package org.starter.project.domain.service

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultHandler @Inject constructor(
    val dispatcher: CoroutineDispatcher
) {
    suspend inline fun <Success> async(
        dispatcher: CoroutineDispatcher = this.dispatcher,
        crossinline block: suspend () -> Success
    ): Result<Success> {
        return withContext(dispatcher) {
            try {
                val value = block()
                Result.success(value)
            } catch (e: Throwable) {
                // TODO: report error to your analytics
                Log.d(this@ResultHandler::class.simpleName, e.message.orEmpty())
                coroutineContext.ensureActive()
                Result.failure(e)
            }
        }
    }

    suspend inline fun asyncUnit(
        dispatcher: CoroutineDispatcher = this.dispatcher,
        crossinline block: suspend () -> Unit
    ): Result<Unit> = async(dispatcher) { block() }

    inline fun <Success> immediate(
        crossinline block: () -> Success
    ): Result<Success> {
        return try {
            val value = block()
            Result.success(value)
        } catch (e: Throwable) {
            // TODO: report error to your analytics
            Log.d(this@ResultHandler::class.simpleName, e.message.orEmpty())
            Result.failure(e)
        }
    }

    inline fun immediateUnit(
        crossinline block: () -> Unit
    ): Result<Unit> = immediate { block() }
}
