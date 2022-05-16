package com.example.zennextest.presentation.viewNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zennextest.R
import com.example.zennextest.domain.DomainNews
import com.example.zennextest.presentation.NetworkStatus

class NewsRecyclerAdapter(
    private val listener: (id: DomainNews) -> Unit
) : PagedListAdapter<DomainNews, NewsRecyclerAdapter.MyViewHolder>(NewsComparator) {
    private var networkStatus: NetworkStatus? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val title: TextView = itemView.findViewById(R.id.tv_title)
        val description: TextView = itemView.findViewById(R.id.tv_description)
        val published: TextView = itemView.findViewById(R.id.tv_published)
        val image: ImageView = itemView.findViewById(R.id.iv_news)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            getItem(layoutPosition)?.let { listener.invoke(it) }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = getItem(position)?.title
        holder.description.text = getItem(position)?.description
        holder.published.text = getItem(position)?.publishedAt
        Glide.with(holder.itemView.context).load(getItem(position)?.urlToImage).into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{

        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_view_news, parent, false)
        return MyViewHolder(itemView)
    }

    private fun hasExtraRow() = networkStatus != null && networkStatus != NetworkStatus.Success

    override fun getItemViewType(position: Int): Int {
            return R.layout.recycler_item_view_news
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
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
