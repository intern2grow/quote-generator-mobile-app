package dev.awd.quotegen.domain.usecases

import dev.awd.quotegen.domain.repository.QuotesRepository

class GetFavoriteQuotesUseCase(private val quotesRepository: QuotesRepository) {
    suspend operator fun invoke() = quotesRepository.getFavoriteQuotes()
}