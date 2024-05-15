package com.example.quickstartlessons.module.resetpassword.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quickstartlessons.databinding.FragmentResetPasswordSMSBinding

class ResetPasswordSMSFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordSMSBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordSMSBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = ResetPasswordSMSFragment()
    }
}