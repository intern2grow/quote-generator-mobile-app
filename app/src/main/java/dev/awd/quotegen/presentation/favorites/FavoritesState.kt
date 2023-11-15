package dev.awd.quotegen.presentation.favorites

import dev.awd.quotegen.domain.models.QuoteModel

data class FavoritesState(

    val quotes: List<QuoteModel> = emptyList(),
    val loading: Boolean = true,
    val empty: Boolean = quotes.isEmpty()
)

