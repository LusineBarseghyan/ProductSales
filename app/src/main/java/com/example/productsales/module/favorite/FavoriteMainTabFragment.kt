package com.example.quickstartlessons.module.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.core.room.FavoriteManager
import com.example.quickstartlessons.databinding.FragmentFavoriteMainTabBinding
import com.example.quickstartlessons.module.base.fragment.BaseFragment
import com.example.quickstartlessons.module.home.ui.HomeMainTabFragmentDirections
import com.example.quickstartlessons.module.home.ui.adapter.ProductAdapter
import org.koin.android.ext.android.inject


class FavoriteMainTabFragment : BaseFragment() {
    private lateinit var binding: FragmentFavoriteMainTabBinding
    private val favoriteManager: FavoriteManager by inject()

    private val adapter = ProductAdapter(onItemClick = {
        findNavController().navigate(HomeMainTabFragmentDirections.actionGlobalProductDetailsFragment(it.id.toString()))
    }, updateFavorite = { isFavorite,product->
        if (isFavorite) favoriteManager.insertProduct(product) else favoriteManager.deleteProductById(product)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMainTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserve()

    }

    private fun setupViews() {
        binding.recyclerViewFavorite.adapter = adapter
        binding.recyclerViewFavorite.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserve() {
        favoriteManager.getAllProduct().observe(viewLifecycleOwner) {
            val products = it.map { productEntity ->
                ProductDto(
                    id = productEntity.id,
                    title = productEntity.title,
                    description = productEntity.description,
                    price = productEntity.price,
                    brand = productEntity.brand,
                    thumbnail = productEntity.thumbnail,
                    stock = 0,
                    rating = 0.0,
                    discountPercentage = 0.0,
                    image = listOf()
                )

            }
            adapter.updateData(products)
            val favoriteIds = it.map { productEntity -> productEntity.id }
            adapter.updateFavorites(favoriteIds)

            if (products.isEmpty() && favoriteIds.isEmpty()) {
                binding.textView.isVisible = true
            } else {
                products.isNotEmpty()
                binding.textView.isVisible = false
            }
        }

    }

}


