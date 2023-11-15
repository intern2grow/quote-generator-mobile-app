package dev.awd.quotegen.presentation.home

import dev.awd.quotegen.domain.models.QuoteModel

data class HomeState(

    val quote: QuoteModel? = null,
    val loading: Boolean = true,
    val favoriteQuotesCount: String = ""
)

