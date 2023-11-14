package dev.awd.quotegen.data.repository

import dev.awd.quotegen.data.local.QuoteDao
import dev.awd.quotegen.data.mappers.toQuoteEntity
import dev.awd.quotegen.data.mappers.toQuoteModel
import dev.awd.quotegen.data.Result
import dev.awd.quotegen.data.remote.QuoteApi
import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class QuotesRepoImpl(
    private val quoteApi: QuoteApi,
    private val quoteDao: QuoteDao
) : QuotesRepository {
    companion object {
        private const val TAG = "QuotesRepository"
    }

    override suspend fun getRandomQuotes() = flowOf(
        try {
            val quotes = quoteApi.getRandomQuotes().map {
                it.toQuoteModel()
            }
            Result.Success(quotes)
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Unknown Exception")
        }
    ).flowOn(Dispatchers.IO)

    override suspend fun getFavoriteQuotes() = flowOf(
        try {
            val favorites = quoteDao.getAllQuotes().map {
                it.toQuoteModel()
            }
            Result.Success(favorites)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e.message ?: "Unknown Exception")
        }
    ).flowOn(Dispatchers.IO)

    override suspend fun addNewQuote(quoteModel: QuoteModel) =
        try {
            quoteDao.insert(quoteModel.toQuoteEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    override suspend fun deleteQuote(quoteModel: QuoteModel) =
        try {
            quoteDao.delete(quoteModel.toQuoteEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }


}