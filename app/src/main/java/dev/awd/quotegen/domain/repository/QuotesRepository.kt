package dev.awd.quotegen.domain.repository

import dev.awd.quotegen.data.Result
import dev.awd.quotegen.domain.models.QuoteModel
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun getRandomQuotes(): Flow<Result>
    suspend fun getFavoriteQuotes(): Flow<Result>
    suspend fun addNewQuote(quoteModel: QuoteModel)
    suspend fun deleteQuote(quoteModel: QuoteModel)
}