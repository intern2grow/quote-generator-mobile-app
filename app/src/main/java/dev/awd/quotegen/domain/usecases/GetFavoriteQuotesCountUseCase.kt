package dev.awd.quotegen.domain.usecases

import dev.awd.quotegen.domain.repository.QuotesRepository

class GetFavoriteQuotesCountUseCase(private val quotesRepository: QuotesRepository) {
    suspend operator fun invoke() = quotesRepository.getQuotesCount()
}