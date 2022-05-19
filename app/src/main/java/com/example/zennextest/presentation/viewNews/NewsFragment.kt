package com.example.zennextest.presentation.viewNews

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.zennextest.R
import com.example.zennextest.databinding.NewsFragmentBinding
import com.example.zennextest.domain.NetworkStatus
import com.example.zennextest.data.network.WiFiService
import com.example.zennextest.ui.extension.showGeneralErrorDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.news_fragment) {
    private val binding by viewBinding(NewsFragmentBinding::bind)
    private val viewModel by viewModels<NewsViewModel>()
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

        checkInternet()

//        Не понимаю почему это не работает. В прошлом проекте Multitasker
//        (https://github.com/AlexChistozvonov/multitaskerr) делал обработку
//        ошибок через viewState, event. С LiveData почему-то возникли тружности.

//        viewModel.networkStatus.observe(viewLifecycleOwner) {
//            if (it == NetworkStatus.Error) {
//                progressBar.hide()
//                showGeneralErrorDialog(
//                    context = requireContext()
//                )
//            }
//            if (it == NetworkStatus.Loading) progressBar.show()
//            if (it == NetworkStatus.Success) progressBar.hide()
//        }
    }

    private fun checkInternet() {
        WiFiService.instance.initializeWithApplicationContext(requireContext())
        if (!WiFiService.instance.isOnline()) {
            showGeneralErrorDialog(
                context = requireContext()
            )
        } else initRecyclerView()
    }

    private fun scrollToTop() {
        binding.recycler.scrollToPosition(0)
    }

    private fun refresh() {
        viewModel.refresh()
        checkInternet()
    }

    private fun initRecyclerView() = with(binding) {

        viewModel.refreshStatus.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it == NetworkStatus.Loading
            if (it == NetworkStatus.Success) {
                scrollToTop()
            }
        }
        swipeRefresh.setOnRefreshListener {
            refresh()
        }

        viewModel.networkStatus.observe(viewLifecycleOwner) {
            listAdapter.setNetworkState(it)
        }

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = listAdapter
        viewModel.news.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }
}
