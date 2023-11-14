package dev.awd.quotegen.domain.usecases

import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.domain.repository.QuotesRepository

class RemoveFavoriteQuoteUseCase(private val quotesRepository: QuotesRepository) {
    suspend operator fun invoke(quoteModel: QuoteModel) = quotesRepository.deleteQuote(quoteModel)
}