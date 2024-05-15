package com.example.quickstartlessons.module.launch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.quickstartlessons.R
import com.example.quickstartlessons.module.base.activity.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setConditionalStartDestination(navHostFragment.navController, R.id.splashFragment)

    }

    private fun setConditionalStartDestination(navController: NavController, @IdRes startDestId: Int, bundle: Bundle? = null) {
        navController.navInflater.inflate(R.navigation.nav_graph_home_general).apply {
            setStartDestination(startDestId)
            navController.setGraph(this, bundle)
        }
    }
}