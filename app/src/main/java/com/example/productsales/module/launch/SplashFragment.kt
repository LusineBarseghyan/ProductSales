package com.example.quickstartlessons.module.launch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quickstartlessons.MainActivity
import com.example.quickstartlessons.QSApplication
import com.example.quickstartlessons.databinding.FragmentSplashBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import com.example.quickstartlessons.module.base.utils.PreferencesManager
import com.example.quickstartlessons.module.base.utils.splashActivity

class SplashFragment : BaseFragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivity?.viewModel?.getUsers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserve()
    }

    private fun setupObserve() {
        splashActivity?.viewModel?.usersLiveData?.observe(viewLifecycleOwner) { it ->
            if (PreferencesManager.getUserName() != null && PreferencesManager.getUserPassword() != null) {
                val user = it?.users?.find {
                    it.username == PreferencesManager.getUserName()
                }
                user?.let {
                    QSApplication.userProfileLiveData.value = it
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
            } else {
                findNavController().navigate(SplashFragmentDirections.actionGlobalSignInFragment())
            }
        }
    }

}
