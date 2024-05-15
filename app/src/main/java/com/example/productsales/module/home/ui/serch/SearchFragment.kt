package com.example.quickstartlessons.module.home.ui.serch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quickstartlessons.R
import com.example.quickstartlessons.core.room.FavoriteManager
import com.example.quickstartlessons.databinding.FragmentSearchBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import com.example.quickstartlessons.module.home.ui.HomeMainTabFragmentDirections
import com.example.quickstartlessons.module.home.ui.adapter.ProductAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val favoriteManager: FavoriteManager by inject()
    private val adapter = ProductAdapter(onItemClick = {
        findNavController().navigate(HomeMainTabFragmentDirections.actionGlobalProductDetailsFragment(it.id.toString()))
    }, updateFavorite = { isFavorite, product ->
        if (isFavorite) favoriteManager.insertProduct(product) else favoriteManager.deleteProductById(product)
    })
    private val viewModel by viewModel<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupView()
        observerLiveData()
    }


    private fun setupView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setupListener() {
        binding.toolBar.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.searchEditText.doAfterTextChanged {
            if (it != null && it.length > 2) {
                viewModel.search(true, it.toString())
            } else {
                viewModel.clearValue()
            }
        }
    }

    private fun observerLiveData() {
        viewModel.searchProductLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            binding.textView.isVisible = it.isNullOrEmpty()
        }
        viewModel.searchProductErrorLiveData.observe(viewLifecycleOwner) {
            showErrorMessageDialog(getString(R.string.error_data), it.toString())
        }
    }
}