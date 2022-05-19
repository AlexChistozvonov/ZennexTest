package com.example.zennextest.presentation.viewNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zennextest.databinding.RecyclerItemNewsBinding
import com.example.zennextest.domain.models.DomainNews

class NewsViewHolder internal constructor(
    private val binding: RecyclerItemNewsBinding,
    private val listener: (id: DomainNews) -> Unit
) :
    RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {
    val title = binding.tvTitle
    val description = binding.tvDescription
    val published = binding.tvPublished
    val image = binding.ivNews

    init {
        binding.newsContainer.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        binding.news?.let { listener.invoke(it) }
    }

    companion object {
        fun from(parent: ViewGroup, listener: (id: DomainNews) -> Unit): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerItemNewsBinding.inflate(inflater, parent, false)
            return NewsViewHolder(binding, listener)
        }
    }
}