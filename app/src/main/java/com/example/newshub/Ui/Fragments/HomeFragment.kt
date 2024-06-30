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
import com.example.newshub.Utils.WebViewNavigator
import com.example.newshub.ViewModel.NewsViewModel
import com.example.newshub.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    private val newsViewModel: NewsViewModel by viewModels()

    @Inject
    lateinit var webViewNavigator: WebViewNavigator
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = binding.swipeRefreshLayout
        setupRecyclerView()
        setupObservers()

        newsViewModel.getHeadlines("in", 1)
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
        //  Recyclerview item onclick
            webViewNavigator.openWebView(parentFragmentManager, url)
        }

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun refreshData() {
        newsViewModel.getHeadlines("in", 1)
        Handler(Looper.getMainLooper()).postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
