package com.example.quickstartlessons.module.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quickstartlessons.QSApplication
import com.example.quickstartlessons.databinding.FragmentAccountMainTabBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment


class AccountMainTabFragment : BaseFragment() {
    private lateinit var binding: FragmentAccountMainTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountMainTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settings.setOnClickListener {
            findNavController().navigate(AccountMainTabFragmentDirections.actionGlobalSettingsFragment())
        }
        observeLiveData()
       binding.posts.setOnClickListener{
           findNavController().navigate(AccountMainTabFragmentDirections.actionGlobalPostFragment())
       }
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData() {
        QSApplication.userProfileLiveData.observe(viewLifecycleOwner) {
           val user=it
            binding.emailAddress.text = user?.email
            if (user != null) {
                binding.userName.text = user.firstName + " " + user.lastName
                binding.personalInformation.setOnClickListener {
                    findNavController().navigate(AccountMainTabFragmentDirections.actionGlobalPersonalDataFragment())
                }

            }
        }
    }
}


