package com.riski.greenadvisor.ui.detail.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riski.greenadvisor.data.response.DataAnyItem
import com.riski.greenadvisor.databinding.ItemListAnyPlantBinding
import com.riski.greenadvisor.ui.detail.detailanyplant.DetailAnyPlantActivity
import com.riski.greenadvisor.utils.DiffUtilsAnyPlant

class DetailShowListAnyPlantAdapter: RecyclerView.Adapter<DetailShowListAnyPlantAdapter.AnyPlantListView>() {

    private val list = ArrayList<DataAnyItem>()

    fun setAnyPlant(anyPlantList: List<DataAnyItem>) {
        val diff = DiffUtilsAnyPlant(this.list, anyPlantList)
        val diffResult = DiffUtil.calculateDiff(diff)

        this.list.clear()
        this.list.addAll(anyPlantList)
        diffResult.dispatchUpdatesTo(this)
        Log.d("AnyPlant", "Data Size: ${anyPlantList.size}")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailShowListAnyPlantAdapter.AnyPlantListView {
        val view = ItemListAnyPlantBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return AnyPlantListView(view)
    }

    override fun onBindViewHolder(holder: AnyPlantListView, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class AnyPlantListView(private var binding: ItemListAnyPlantBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val anyPlant = list[adapterPosition]
                val intent = Intent(itemView.context, DetailAnyPlantActivity::class.java)
                intent.putExtra(DetailAnyPlantActivity.EXTRA_ANY, anyPlant)
                it.context.startActivity(intent)
            }
        }

        fun bind(anyPlant: DataAnyItem) {
            with(binding) {
                Glide.with(itemListAnyPlantImg)
                    .load(anyPlant.foto)
                    .circleCrop()
                    .into(binding.itemListAnyPlantImg)
                binding.itemListAnyPlantText.text = anyPlant.namaTanaman
            }
        }
    }
}