package com.example.quickstartlessons.module.resetpassword

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ResetPasswordViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: List<Fragment>?) {
        fragments.clear()
        items?.let {
            fragments.addAll(it)
        }
        notifyDataSetChanged()
    }
}