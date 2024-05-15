package com.example.quickstartlessons.module.resetpassword.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quickstartlessons.databinding.FragmentResetPasswordEmailBinding

class ResetPasswordEmailFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = ResetPasswordEmailFragment()
    }
}