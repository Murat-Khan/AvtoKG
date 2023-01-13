package com.murat.avtokg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murat.avtokg.databinding.ItemModelBinding


class ModelAdapter (private val modelList :ArrayList<String>,private val listener: OnItemClickListener): RecyclerView.Adapter<ModelAdapter.ModelViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
      return  ModelViewHolder(ItemModelBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,false))
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

   inner class ModelViewHolder (private val binding: ItemModelBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(s :String) {
            binding.tvModel.text = s
            binding.root.setOnClickListener {
                listener.onItemClick(s)
            }

        }

    }

    interface OnItemClickListener{
        fun onItemClick(carModel : String)
    }
}