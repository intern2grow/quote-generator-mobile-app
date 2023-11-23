package dev.awd.quotegen.domain.usecases

import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.domain.repository.QuotesRepository

class AddFavoriteQuoteUseCase(private val quotesRepository: QuotesRepository) {
    suspend operator fun invoke(quoteModel: QuoteModel) = quotesRepository.addNewQuote(quoteModel)
}