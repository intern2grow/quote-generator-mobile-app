package dev.awd.quotegen.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.awd.quotegen.databinding.FavQuoteCardBinding
import dev.awd.quotegen.domain.models.QuoteModel
import dev.awd.quotegen.utils.CustomClickListener

class FavoritesAdapter(
    private val onItemDeleted: (QuoteModel) -> Unit
) : ListAdapter<QuoteModel, RecyclerView.ViewHolder>(FavDiffUtil()) {

    inner class ViewHolder constructor(
        private var binding: FavQuoteCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuoteModel) {
            binding.quote = item
            binding.onDelete = object : CustomClickListener {
                override fun onClick(quoteModel: QuoteModel) {
                    onItemDeleted(quoteModel)
                    this@FavoritesAdapter.notifyItemRemoved(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavQuoteCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentQuote = getItem(position)
        if (holder is ViewHolder) holder.bind(currentQuote)
    }

}

class FavDiffUtil : DiffUtil.ItemCallback<QuoteModel>() {
    override fun areItemsTheSame(
        oldItem: QuoteModel,
        newItem: QuoteModel
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: QuoteModel,
        newItem: QuoteModel
    ): Boolean {
        return oldItem == newItem
    }

}