package com.example.quickstartlessons.module.account.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickstartlessons.databinding.FragmentAppLanguageBinding
import com.example.quickstartlessons.module.account.settings.adapter.AppLanguageAdapter
import com.example.quickstartlessons.module.base.model.LocaleEnum
import com.example.quickstartlessons.module.base.model.SelectableData
import com.example.quickstartlessons.module.base.utils.LocaleSwitcher
import com.example.quickstartlessons.module.base.utils.PreferencesManager
import com.example.quickstartlessons.module.base.utils.homeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class AppLanguageFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAppLanguageBinding
   private val language= mutableListOf(LocaleEnum.ENGLISH,LocaleEnum.ARMENIAN,LocaleEnum.RUSSIAN)
    private val adapter = AppLanguageAdapter {
         LocaleSwitcher.updateLocale(requireContext(), Locale(it.languageCode))
        homeActivity?.recreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }


    private fun setupView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val data=language.map {
            SelectableData(data = it, isSelected = it.languageCode==PreferencesManager.getCurrentLanguage())
        }
        adapter.updateData(data)

    }


}