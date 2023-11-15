package dev.awd.quotegen.presentation.favorites

import android.os.Bundle
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
import dev.awd.quotegen.databinding.FragmentFavoritesBinding
import dev.awd.quotegen.utils.viewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels(ownerProducer = { this }) {
        viewModelFactory {
            QuoteGenApp.appModule.run {
                FavoritesViewModel(
                    getFavoriteQuotesUseCase,
                    removeFavoriteQuoteUseCase
                )
            }
        }
    }

    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesAdapter by lazy {
        FavoritesAdapter {
            viewModel.setIntent(FavoritesIntent.RemoveFromFavorites(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.adapter = favoritesAdapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.onNavtoHome = FavoritesIntent.OnNavToHome
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setIntent(FavoritesIntent.GetFavoritesQuotes)
        listenToFavoritesEffect()
    }

    private fun listenToFavoritesEffect() {
        lifecycleScope.launch {
            viewModel.favoritesEffect.collectLatest { effect ->
                when (effect) {
                    is FavoritesEffect.NavToHomeEffect -> {
                        findNavController().navigate(R.id.action_favoritesFragment_to_homeFragment)
                    }

                    is FavoritesEffect.ShowSnackbar -> {
                        Snackbar.make(binding.root, effect.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}