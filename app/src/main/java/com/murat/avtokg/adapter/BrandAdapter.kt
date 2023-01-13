package com.murat.avtokg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murat.avtokg.R
import com.murat.avtokg.databinding.ItemBrandBinding
import com.murat.avtokg.ui.brandfragment.Brand

class BrandAdapter(private val listener: OnItemClickListener) :RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    private val brandList = arrayListOf(
        Brand(image = R.drawable.ic_mers, name = "Mercedes Benz", arrayListOf("Е-Класс", "С-класс", "AMG GT")),
        Brand(image = R.drawable.ic_volkswagen, name = "Volkswagen", arrayListOf("Passat", "Golf", "Vento")),
        Brand(image = R.drawable.ic_toyota, name = "Toyota", arrayListOf("Camry", "Land Cruiser", "Corolla")),
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(ItemBrandBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
       holder.bind(brandList[position])
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

   inner class BrandViewHolder (private val binding: ItemBrandBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(brand: Brand) {
            with(binding){
               tvBrandName.text = brand.name
                Glide.with(imgBrand).load(brand.image).into(imgBrand)
                root.setOnClickListener {
                    listener.onItemClick(brand)

                }

            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(brand: Brand)
    }
}