package com.example.zennextest.presentation.viewNews

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zennextest.R
import com.example.zennextest.domain.models.DomainNews
import com.example.zennextest.domain.NetworkStatus

class NewsRecyclerAdapter(
    private val listener: (id: DomainNews) -> Unit,
) : PagedListAdapter<DomainNews, RecyclerView.ViewHolder>(NewsComparator) {
    private var networkStatus: NetworkStatus? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int): Unit =
        with(holder as NewsViewHolder) {
            title.text = getItem(position)?.title
            description.text = getItem(position)?.description
            published.text = getItem(position)?.publishedAt
            Glide.with(itemView.context).load(getItem(position)?.urlToImage).into(image)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder.from(parent, listener)
    }

    private fun hasExtraRow() = networkStatus != null && networkStatus != NetworkStatus.Success

    override fun getItemViewType(position: Int): Int {
        return R.layout.recycler_item_news
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkStatus: NetworkStatus?) {
        val previousState = this.networkStatus
        val hadExtraRow = hasExtraRow()
        this.networkStatus = newNetworkStatus
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkStatus) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val NewsComparator = object : DiffUtil.ItemCallback<DomainNews>() {
            override fun areItemsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: DomainNews, newItem: DomainNews): Boolean =
                oldItem == newItem
        }
    }
}
