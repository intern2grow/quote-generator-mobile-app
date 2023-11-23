package dev.awd.quotegen.presentation.home

import dev.awd.quotegen.domain.models.QuoteModel

sealed interface HomeIntent {
    object GetRandomQuote : HomeIntent
    data class AddToFavorites(val quote: QuoteModel) : HomeIntent
    data class RemoveFromFavorites(val quote: QuoteModel) : HomeIntent
    object OnNavToFav : HomeIntent

}