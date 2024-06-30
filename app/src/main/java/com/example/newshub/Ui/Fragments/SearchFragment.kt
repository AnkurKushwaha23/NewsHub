package com.example.newshub.Ui.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newshub.Adapter.NewsAdapter
import com.example.newshub.Ui.MainActivity
import com.example.newshub.Utils.WebViewNavigator
import com.example.newshub.ViewModel.NewsViewModel
import com.example.newshub.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var webViewNavigator: WebViewNavigator
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchQuery = it.getString(ARG_SEARCH_QUERY) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        setupRecyclerView()
        setupObservers()
        (activity as? MainActivity)?.setTabLayoutVisibility(false)
        newsViewModel.getSearchNews(searchQuery)
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
    }


    private fun setupObservers() {
        newsViewModel.newsData.observe(viewLifecycleOwner) { newsModel ->
            newsAdapter.submitList(newsModel.articles)
        }
    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter { url ->
            webViewNavigator.openWebView(parentFragmentManager, url,true)
        }

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun refreshData() {
        newsViewModel.getSearchNews(searchQuery)
        Handler(Looper.getMainLooper()).postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }


    companion object {
        private const val ARG_SEARCH_QUERY = "arg_search_query"

        @JvmStatic
        fun newInstance(searchQuery: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SEARCH_QUERY, searchQuery)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? MainActivity)?.setTabLayoutVisibility(true)
    }
}