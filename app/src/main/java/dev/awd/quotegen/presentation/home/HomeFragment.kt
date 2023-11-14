package dev.awd.quotegen.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.awd.quotegen.QuoteGenApp
import dev.awd.quotegen.databinding.FragmentHomeBinding
import dev.awd.quotegen.utils.viewModelFactory

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { this }) {
        viewModelFactory {
            HomeViewModel(
                QuoteGenApp.appModule.getRandomQuotesUseCase
            )
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
//        binding.onAddToFavorites = HomeIntent.AddToFavorites(QuoteModel())
//        binding.onRemoveFromFavorites = HomeIntent.RemoveFromFavorites
        binding.onNavToFavorites = HomeIntent.OnNavToFav

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setIntent(HomeIntent.GetRandomQuote)
    }
}