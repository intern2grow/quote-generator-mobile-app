package dev.awd.quotegen.presentation.favorites

import dev.awd.quotegen.domain.models.QuoteModel

sealed interface FavoritesIntent {
    object GetFavoritesQuotes : FavoritesIntent
    data class RemoveFromFavorites(val quote: QuoteModel) : FavoritesIntent
    object OnNavToHome : FavoritesIntent

}