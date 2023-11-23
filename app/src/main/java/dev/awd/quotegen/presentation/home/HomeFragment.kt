package dev.awd.quotegen.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.awd.quotegen.QuoteGenApp
import dev.awd.quotegen.R
import dev.awd.quotegen.databinding.FragmentHomeBinding
import dev.awd.quotegen.utils.viewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { this }) {
        viewModelFactory {
            QuoteGenApp.appModule.run {

                HomeViewModel(
                    getRandomQuotesUseCase,
                    addFavoriteQuoteUseCase,
                    getFavoriteQuotesCountUseCase
                )
            }
        }
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.onGetNewQuote = HomeIntent.GetRandomQuote
        binding.onNavToFavorites = HomeIntent.OnNavToFav

//        binding.onRemoveFromFavorites = HomeIntent.RemoveFromFavorites

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setIntent(HomeIntent.GetRandomQuote)
        lifecycleScope.launch {
            viewModel.homeState.collectLatest { homeState ->
                Log.d(TAG, "onViewCreated: ${homeState.favoriteQuotesCount}")
                homeState.quote?.let {
                    binding.onAddToFavorites = HomeIntent.AddToFavorites(it)
                }
            }
        }
        listenToHomeEffects()
    }

    private fun listenToHomeEffects() {
        lifecycleScope.launch {
            viewModel.homeEffect.collectLatest { effect ->
                when (effect) {
                    is HomeEffect.NavToFavEffect -> navToFav()
                    is HomeEffect.ShowSnackbar -> {
                        Snackbar.make(binding.root, effect.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navToFav() {
        findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}