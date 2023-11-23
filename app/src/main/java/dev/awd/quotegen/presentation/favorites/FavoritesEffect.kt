package dev.awd.quotegen.presentation.favorites

sealed class FavoritesEffect {
    object NavToHomeEffect : FavoritesEffect()
    data class ShowSnackbar(val msg: String) : FavoritesEffect()
}
