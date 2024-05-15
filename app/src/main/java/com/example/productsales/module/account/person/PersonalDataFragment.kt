package com.example.quickstartlessons.module.account.person

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quickstartlessons.QSApplication
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.FragmentPersonalDataBinding


class PersonalDataFragment : Fragment() {
    private lateinit var binding: FragmentPersonalDataBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPersonData()
        binding.toolBar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupPersonData() {
        QSApplication.userProfileLiveData.observe(viewLifecycleOwner) {
            val user = it
            binding.age.text = getString(R.string.age) + "։" + user?.age
            binding.birthDate.text = getString(R.string.birth_date) + "։" + user?.birthDate
            binding.gender.text = getString(R.string.gender) + "։" + user?.gender
            binding.phone.text = getString(R.string.phone) + "։" + user?.phone
        }
    }
}

