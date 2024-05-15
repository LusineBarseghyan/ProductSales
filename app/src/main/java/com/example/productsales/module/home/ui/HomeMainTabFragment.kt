package com.example.quickstartlessons.module.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickstartlessons.R
import com.example.quickstartlessons.core.room.FavoriteManager
import com.example.quickstartlessons.databinding.FragmentHomeMainTabBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import com.example.quickstartlessons.module.home.ui.adapter.CategoriesAdapter
import com.example.quickstartlessons.module.home.ui.adapter.ProductAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeMainTabFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeMainTabBinding
    private val favoriteManager: FavoriteManager by inject()
    private val viewModel by viewModel<HomeMainTabViewModel>()
    private val adapter = ProductAdapter(onItemClick = {
        findNavController().navigate(HomeMainTabFragmentDirections.actionGlobalProductDetailsFragment(it.id.toString()))
    }, updateFavorite = { isFavorite, product ->
        if (isFavorite) favoriteManager.insertProduct(product) else favoriteManager.deleteProductById(product)
    })
    private val adapterCategories = CategoriesAdapter { category ->

        if (category == getString(R.string.products)) {
            viewModel.getProduct()
        } else {
            viewModel.geProductByCategory(true, category)
        }
        binding.toolBarName.text = category.replaceFirstChar {
            it.uppercase()
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProduct()
        viewModel.getCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMainTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserve()
        setupListener()
    }

    private fun setupListener() {
        binding.toolBar.setOnClickListener {
            findNavController().navigate(HomeMainTabFragmentDirections.actionGlobalSearchFragment())
        }
    }

    private fun setupViews() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCategory.adapter = adapterCategories
        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupObserve() {
        viewModel.productLiveData.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
        viewModel.productErrorLiveData.observe(viewLifecycleOwner) {
            showErrorMessageDialog(getString(R.string.error_data), it.toString())
        }
        favoriteManager.getAllProduct().observe(viewLifecycleOwner) {
            val favoriteIds = it.map { productEntity -> productEntity.id }
            adapter.updateFavorites(favoriteIds)
        }
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            val list = it?.toMutableList()
            list?.add(0, getString(R.string.products))
            adapterCategories.updateData(list)
        }

        viewModel.categoryErrorLiveData.observe(viewLifecycleOwner) {
            showErrorMessageDialog(getString(R.string.error_data), it.toString())
        }

    }
}
