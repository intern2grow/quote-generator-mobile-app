package dev.awd.quotegen.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.awd.quotegen.data.Result
import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.domain.usecases.GetRandomQuotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val getRandomQuotesUseCase: GetRandomQuotesUseCase
) : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    var homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
        private set
    private var quotes: MutableStateFlow<List<QuoteModel>> =
        MutableStateFlow(emptyList())

    init {
        loadAllQuotes()
    }

    private fun loadAllQuotes() {
        viewModelScope.launch {
            getRandomQuotesUseCase().collectLatest { apiResponse ->
                when (apiResponse) {
                    is Result.Success<*> -> {
                        quotes.update {
                            apiResponse.data as List<QuoteModel>
                        }
                    }

                    is Result.Loading -> homeState.update {
                        it.copy(
                            loading = true
                        )
                    }

                    is Result.Failure -> {
                        Log.e(TAG, "Can't get Quotes: ${apiResponse.error}")
                    }
                }
            }
        }
    }

    fun setIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GetRandomQuote -> getRandomQuote()
            is HomeIntent.AddToFavorites -> addToFavorites()
            is HomeIntent.RemoveFromFavorites -> removeFromFavorites()
            is HomeIntent.OnNavToFav -> navigateToFavorites()
        }
    }

    private fun getRandomQuote() {
        viewModelScope.launch {
            quotes.collectLatest { quotesList ->
                val randomQuote = quotesList.randomOrNull()
                homeState.update {
                    it.copy(loading = false, quote = randomQuote)
                }
            }
        }
    }

    private fun navigateToFavorites() {
        TODO("Not yet implemented")
    }

    private fun removeFromFavorites() {
        TODO("Not yet implemented")
    }

    private fun addToFavorites() {
        TODO("Not yet implemented")
    }
}