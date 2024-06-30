package com.example.newshub.Ui.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.newshub.R
import com.example.newshub.Ui.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WebFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var fabShare: FloatingActionButton
    private var url: String? = null
    private var bool: Boolean? = null

    companion object {
        private const val ARG_URL = "url"
        private const val ARG_BOOL = "boolean_key"
        fun newInstance(url: String, boolean: Boolean): WebFragment {
            val fragment = WebFragment()
            val args = Bundle()
            args.putString(ARG_URL, url)
            args.putBoolean(ARG_BOOL, boolean)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
            bool = it.getBoolean(ARG_BOOL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_web, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressBar)
        fabShare = view.findViewById(R.id.fabShare)

        url?.let {
            setupWebView(it)
            setupShareButton(it)
            (activity as? MainActivity)?.setTabLayoutVisibility(false)
            (activity as? MainActivity)?.toggleSearchAndContact(true)
        }
    }

    private fun setupWebView(url: String) {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
        webView.loadUrl(url)
    }

    private fun setupShareButton(url: String) {
        fabShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this article: $url")
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, "Share article via")
            startActivity(chooser)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bool == true) {
            (activity as? MainActivity)?.setTabLayoutVisibility(false)
        } else {
            (activity as? MainActivity)?.setTabLayoutVisibility(true)
        }
        (activity as? MainActivity)?.toggleSearchAndContact(false)
    }

}