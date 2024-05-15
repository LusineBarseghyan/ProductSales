package com.example.quickstartlessons.module.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.FragmentResetPasswordViewPagerBinding
import com.example.quickstartlessons.module.resetpassword.email.ResetPasswordEmailFragment
import com.example.quickstartlessons.module.resetpassword.sms.ResetPasswordSMSFragment
import com.google.android.material.tabs.TabLayoutMediator

class ResetPasswordViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordViewPagerBinding
    private lateinit var adapter: ResetPasswordViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.imageViewBack.setOnClickListener {
            findNavController().navigateUp()
        }
        adapter = ResetPasswordViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        val fragments = mutableListOf(ResetPasswordSMSFragment.newInstance(), ResetPasswordEmailFragment.newInstance())
        binding.viewPager.apply {
            adapter = this@ResetPasswordViewPagerFragment.adapter
            offscreenPageLimit = fragments.size
            isUserInputEnabled = false
        }
        adapter.updateData(fragments)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (fragments[position]) {
                is ResetPasswordSMSFragment -> tab.text = getString(R.string.sms)
                is ResetPasswordEmailFragment -> tab.text = getString(R.string.email)
            }
        }.attach()
    }

    companion object {
        fun newInstance() = ResetPasswordViewPagerFragment()
    }
}