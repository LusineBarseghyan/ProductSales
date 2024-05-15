package com.example.quickstartlessons.module.account.settings.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quickstartlessons.R
import com.example.quickstartlessons.databinding.ItemChangeLangugaeBinding
import com.example.quickstartlessons.module.base.model.LocaleEnum
import com.example.quickstartlessons.module.base.model.SelectableData


class AppLanguageAdapter(private val onItemClick:(LocaleEnum)->Unit) : RecyclerView.Adapter<AppLanguageAdapter.ChangeLanguageViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private lateinit var context: Context
    private val items = mutableListOf<SelectableData<LocaleEnum>>()


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeLanguageViewHolder {
        return ChangeLanguageViewHolder(ItemChangeLangugaeBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChangeLanguageViewHolder, position: Int) {
        holder.bind(items[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(item:List<SelectableData<LocaleEnum>>?){
        items.clear()
        item?.let {
            items.addAll(item)
        }
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    inner class ChangeLanguageViewHolder(private val binding: ItemChangeLangugaeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.language.setOnClickListener {
                if(adapterPosition!=RecyclerView.NO_POSITION){
                    clearSelections()
                    onItemClick.invoke(items[adapterPosition].data)
                    items[adapterPosition].isSelected=true
                    notifyDataSetChanged()

                }
            }
        }
        private fun clearSelections(){
            items.onEach {
                it.isSelected=false
            }
        }
        fun bind(item: SelectableData<LocaleEnum>) {
            binding.language.text = context.getString(item.data.languageResId)

            val selectedColor=if(item.isSelected)R.color.purple_500 else   R.color.color_black_10
           binding.constraintLanguage.background= ContextCompat.getDrawable(context, selectedColor)
        }
    }
}