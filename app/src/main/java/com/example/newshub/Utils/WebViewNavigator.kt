package com.example.newshub.Utils

import androidx.fragment.app.FragmentManager
import com.example.newshub.R
import com.example.newshub.Ui.Fragments.ContactUsFragment
import com.example.newshub.Ui.Fragments.WebFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebViewNavigator @Inject constructor() {
    fun openWebView(fragmentManager: FragmentManager, url: String,boolean: Boolean = false) {
        val webFragment = WebFragment.newInstance(url,boolean)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, webFragment)
            .addToBackStack(null)
            .commit()
    }
    fun openContactView(fragmentManager: FragmentManager) {
        val contactUsFragment = ContactUsFragment()
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, contactUsFragment)
            .addToBackStack(null)
            .commit()
    }
}