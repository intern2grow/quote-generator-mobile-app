package dev.awd.quotegen.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.presentation.favorites.FavoritesAdapter

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: MutableList<QuoteModel>?) {
    val adapter = recyclerView.adapter as? FavoritesAdapter ?: return
    items?.let {
        adapter.submitList(it)
    }
}

interface CustomClickListener {
    fun onClick(quoteModel: QuoteModel)
}