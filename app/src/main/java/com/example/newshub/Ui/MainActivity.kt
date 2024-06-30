package com.example.newshub.Ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.newshub.Adapter.MyViewPagerAdapter
import com.example.newshub.R
import com.example.newshub.Ui.Fragments.SearchFragment
import com.example.newshub.Utils.NetworkConnectivityService
import com.example.newshub.Utils.NetworkMonitor
import com.example.newshub.Utils.WebViewNavigator
import com.example.newshub.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private var snackbar: Snackbar? = null
    lateinit var toolbar: Toolbar

    @Inject
    lateinit var webViewNavigator: WebViewNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        NetworkMonitor.startNetworkMonitoring(applicationContext)

        NetworkConnectivityService.isConnected.observe(this) { isConnected ->
            if (!isConnected) {
                showNoInternetSnackbar()
            } else {
                dismissNoInternetSnackbar()
            }
        }
        // Initialize Toolbar
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Initialize TabLayout and ViewPager2
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        viewPager.adapter = MyViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Headlines"
                1 -> "Business"
                2 -> "Sports"
                3 -> "Entertainment"
                4 -> "Health"
                5 -> "Science"
                6 -> "Technology"
                else -> null
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val contact = menu.findItem(R.id.action_contact)
        val searchView = searchItem.actionView as SearchView

        contact.setOnMenuItemClickListener {
            webViewNavigator.openContactView(supportFragmentManager)
            true
        }

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                supportActionBar?.title = ""
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                supportActionBar?.title = "NewsHub"
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val fragment = SearchFragment.newInstance(it)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle real-time search if needed
                return true
            }
        })
        return true
    }

    fun setTabLayoutVisibility(isVisible: Boolean) {
        binding.tabLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tabLayout.isEnabled = isVisible
        binding.viewPager.isUserInputEnabled = isVisible
    }

    fun toggleSearchAndContact(hide: Boolean) {
        // Assuming you have menu items with these IDs
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val contactItem = toolbar.menu.findItem(R.id.action_contact)

        searchItem?.apply {
            isVisible = !hide
            isEnabled = !hide
        }

        contactItem?.apply {
            isVisible = !hide
            isEnabled = !hide
        }

        // If the SearchView is already expanded, collapse it when hiding
        if (hide && searchItem?.actionView is SearchView) {
            val searchView = searchItem.actionView as SearchView
            if (!searchView.isIconified) {
                searchView.isIconified = true
            }
        }
    }


    private fun showNoInternetSnackbar() {
        if (snackbar == null || snackbar?.isShown == false) {
            snackbar = Snackbar.make(
                binding.root,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.ok)) {
                snackbar?.dismiss()
            }
            snackbar?.show()
        }
    }

    private fun dismissNoInternetSnackbar() {
        snackbar?.dismiss()
        snackbar = null
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkMonitor.stopNetworkMonitoring(applicationContext)
    }
}
