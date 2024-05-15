package com.example.quickstartlessons.module.home.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickstartlessons.core.data.ProductDto
import com.example.quickstartlessons.databinding.ItemProductDataBinding

class ProductAdapter(
    private val onItemClick: (ProductDto) -> Unit,
    private val updateFavorite: (Boolean, ProductDto) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context
    private val favoriteItems = mutableListOf<Int>()
    private val items: MutableList<ProductDto> = mutableListOf()


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductDataBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(item: List<ProductDto>?) {
        items.clear()
        item?.let {
            items.addAll(item)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(item: List<Int>?) {
        favoriteItems.clear()
        item?.let {
            favoriteItems.addAll(item)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class ProductViewHolder(private val binding: ItemProductDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.productConstraintLayout.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick.invoke(items[adapterPosition])
                    notifyDataSetChanged()
                }
            }
            binding.favoriteProduct.setOnCheckedChangeListener { button, isChecked ->
                if (button.isPressed) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        updateFavorite.invoke(isChecked, items[adapterPosition])
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: ProductDto) {
            Glide.with(context).load(item.thumbnail).into(binding.imageProduct)
            binding.productBrand.text = item.brand
            binding.productPrice.text = "${item.price} $"
            binding.productTitle.text = item.title
            binding.productDescription.text = item.description
            binding.favoriteProduct.isChecked = favoriteItems.contains(item.id)
        }
    }
}