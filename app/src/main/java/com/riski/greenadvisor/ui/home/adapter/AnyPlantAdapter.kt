package com.riski.greenadvisor.ui.home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riski.greenadvisor.data.response.DataAnyItem
import com.riski.greenadvisor.databinding.ItemAnyPlantBinding
import com.riski.greenadvisor.ui.detail.detailanyplant.DetailAnyPlantActivity
import com.riski.greenadvisor.utils.DiffUtilsAnyPlant

class AnyPlantAdapter:RecyclerView.Adapter<AnyPlantAdapter.AnyPlantView>() {

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
    ): AnyPlantAdapter.AnyPlantView {
        val view = ItemAnyPlantBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return AnyPlantView(view)
    }

    override fun onBindViewHolder(holder: AnyPlantView, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class AnyPlantView(private var binding: ItemAnyPlantBinding): RecyclerView.ViewHolder(binding.root) {
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
                Glide.with(anyPlantImg)
                    .load(anyPlant.foto)
                    .circleCrop()
                    .into(binding.anyPlantImg)
            }
        }
    }
}