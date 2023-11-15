package dev.awd.quotegen.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.awd.quotegen.data.Result
import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.domain.usecases.GetFavoriteQuotesUseCase
import dev.awd.quotegen.domain.usecases.RemoveFavoriteQuoteUseCase
import dev.awd.quotegen.presentation.home.HomeViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase,
    private val removeFavoriteQuoteUseCase: RemoveFavoriteQuoteUseCase
) : ViewModel() {


    private var quotes: List<QuoteModel> = emptyList()
    var favoritesState: MutableStateFlow<FavoritesState> = MutableStateFlow(FavoritesState())
        private set
    var favoritesEffect: MutableSharedFlow<FavoritesEffect> = MutableSharedFlow()
        private set


    fun setIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.GetFavoritesQuotes -> loadFavorites()
            is FavoritesIntent.Search -> searchInFavorites(intent.keyword)
            is FavoritesIntent.RemoveFromFavorites -> removeQuote(intent.quote)
            is FavoritesIntent.OnNavToHome -> navToHome()
        }
    }

    private fun searchInFavorites(keyword: String) {
        favoritesState.update { it.copy(loading = true) }
        favoritesState.update {
            it.copy(
                quotes = if (keyword.isBlank()) quotes else {
                    favoritesState.value.quotes.filter { quoteModel ->
                        quoteModel.doesMatchSearchQuery(keyword)
                    }
                }
            )
        }
        favoritesState.update { it.copy(loading = false) }
    }

    private fun navToHome() {
        viewModelScope.launch {
            favoritesEffect.emit(FavoritesEffect.NavToHomeEffect)
        }
    }

    private fun removeQuote(quote: QuoteModel) {
        viewModelScope.launch {
            removeFavoriteQuoteUseCase(quote)
            favoritesEffect.emit(FavoritesEffect.ShowSnackbar("Quote Deleted"))
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            getFavoriteQuotesUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success<*> -> {
                        quotes = result.data as List<QuoteModel>
                        favoritesState.update {
                            it.copy(quotes = quotes, loading = false)
                        }
                        Log.i(TAG, "loadFavorites: ${result.data}")
                    }

                    is Result.Loading -> favoritesState.update {
                        it.copy(
                            loading = true
                        )
                    }

                    is Result.Failure -> {
                        Log.e(HomeViewModel.TAG, "Can't get Quotes: ${result.error}")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "FavoritesViewModel"
    }
}