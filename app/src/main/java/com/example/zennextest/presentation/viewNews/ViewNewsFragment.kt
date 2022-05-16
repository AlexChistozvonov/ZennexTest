package com.example.zennextest.presentation.viewNews

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.zennextest.R
import com.example.zennextest.databinding.ViewNewsFragmentBinding
import com.example.zennextest.presentation.NetworkStatus
import com.example.zennextest.ui.extension.showGeneralErrorDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewNewsFragment : Fragment(R.layout.view_news_fragment) {
    private val binding by viewBinding(ViewNewsFragmentBinding::bind)
    private val viewModel by viewModels<ViewNewsViewModel>()
    private val listAdapter by lazy {
        NewsRecyclerAdapter {
            val url = (it.url)
            val builder = CustomTabsIntent.Builder()
            val customBuilder = builder.build()
            builder.setShowTitle(true)
            customBuilder.launchUrl(requireContext(), Uri.parse(url))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        // Переделать!
        viewModel.networkStatus.observe(viewLifecycleOwner){
           if (it == NetworkStatus.Error){
               showGeneralErrorDialog(
                context = requireContext(),
                exception = Exception("error")
            )
           }
        }
    }

    private fun initRecyclerView() = with(binding) {
        recycler.layoutManager = LinearLayoutManager(context)

        val recyclerView: RecyclerView = recycler
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = listAdapter

        viewModel.news.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }
}
