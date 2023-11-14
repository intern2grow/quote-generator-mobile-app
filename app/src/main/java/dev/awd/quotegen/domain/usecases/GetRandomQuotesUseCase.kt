package dev.awd.quotegen.domain.usecases

import dev.awd.quotegen.domain.repository.QuotesRepository

class GetRandomQuotesUseCase(private val quotesRepository: QuotesRepository) {
    suspend operator fun invoke() = quotesRepository.getRandomQuotes()
}