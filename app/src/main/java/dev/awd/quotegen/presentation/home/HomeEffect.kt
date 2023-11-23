package dev.awd.quotegen.presentation.home

sealed class HomeEffect {
    object NavToFavEffect : HomeEffect()
    data class ShowSnackbar(val msg: String) : HomeEffect()
}
