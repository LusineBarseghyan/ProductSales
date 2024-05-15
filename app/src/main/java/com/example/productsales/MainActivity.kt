package com.example.productsales

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.ActivityMainBinding
import com.example.quickstartlessons.module.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null
    private val navigationDestinationHandler = Handler(Looper.getMainLooper())
    private val navigationDestinationRunnable = Runnable {
        currentNavController?.value?.currentDestination?.id?.let {
            findPrimaryNavigationFragment()?.onStateVisible()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.changeStatusBarColor(activity = this, color = ContextCompat.getColor(this, R.color.status_bar_color))
        savedInstanceState?.clear()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        noConnectionTextView = binding.noConnectionTextView


        setupBottomNavigationBar()
        setupViews()
        observeLiveData()
    }

    private fun setupViews() {
        binding.bottomNavigationView.menu.children.forEachIndexed { index, menuItem ->
            menuItem.title = resources.getStringArray(R.array.navigationMenu_titles_res_ids_Array)[index]
        }
    }

    private fun observeLiveData() {}

    private fun setupBottomNavigationBar() {
        binding.bottomNavigationView.itemIconTintList = null

        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_favorite,
            R.navigation.nav_graph_account
        )

        val controller = binding.bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller

        controller.observe(this) { navController ->
            when (navController.graph.startDestinationId) {
                R.id.homeFakeFragment -> setConditionalStartDestination(navController, R.id.homeFragment)
                R.id.favoriteFakeFragment -> setConditionalStartDestination(navController, R.id.favoriteFragment)
                R.id.accountFakeFragment -> setConditionalStartDestination(navController, R.id.accountFragment)
            }

            navController.addOnDestinationChangedListener { _, _, _ ->
                findPrimaryNavigationFragment()?.onStateInVisible()
                navigationDestinationHandler.removeCallbacks(navigationDestinationRunnable)
                navigationDestinationHandler.postDelayed(navigationDestinationRunnable, NAVIGATION_DESTINATION_DURATION)
            }
        }
    }

    private fun setConditionalStartDestination(navController: NavController, @IdRes startDestId: Int, bundle: Bundle? = null) {
        navController.navInflater.inflate(R.navigation.nav_graph_home_general).apply {
            setStartDestination(startDestId)
            navController.setGraph(this, bundle)
        }
    }

    private fun findPrimaryNavigationFragment() = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.getOrNull(0) as? BaseFragment

    @Deprecated("deprecated")
    override fun onBackPressed() {
        if (currentNavController?.value?.currentDestination?.id == R.id.homeFragment) {
            moveTaskToBack(true)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        private const val NAVIGATION_DESTINATION_DURATION: Long = 500
    }
