package com.murat.avtokg.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.murat.avtokg.R
import com.murat.avtokg.databinding.ItemCarBinding
import com.murat.avtokg.db.CarEntity
import com.murat.avtokg.utils.Constants.EDIT
import com.murat.avtokg.utils.loadImage



class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHOlder>() {
    lateinit var context :  Context
    private val carList = ArrayList<CarEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHOlder {
        context = parent.context
       return ViewHOlder(ItemCarBinding.inflate(
           LayoutInflater.from(
               parent.context),parent,
           false))
    }

    fun setCarList(cars : List<CarEntity>){
        carList.clear()
        carList.addAll(cars)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHOlder, position: Int) {
       holder.onBind(carList[position])

    }

    override fun getItemCount() = carList.size

  inner  class ViewHOlder(private var binding: ItemCarBinding) :RecyclerView.ViewHolder(binding.root) {
        fun onBind(carEntity: CarEntity) {
            with(binding){
                tvYear.text = carEntity.seriesYear
                tvPrice.text = carEntity.price.toString()
                tvBody.text = carEntity.body
                tvModel.text = carEntity.model
                tvBrand.text = carEntity.brand
                imgCar.loadImage(carEntity.image)
                menuImg.setOnClickListener {
                    val popupMenu = PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_items, popupMenu.menu)
                    popupMenu.show()
                    popupMenu.setOnMenuItemClickListener {menuItem ->
                        when(menuItem.itemId){
                            R.id.itemEdit ->{
                                onItemClickListener?.let {
                                    it(carEntity,EDIT)
                                }
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
  }
    private var onItemClickListener: ((CarEntity, String) -> Unit)? = null
    fun setOnItemClickListener(listener: (CarEntity, String) -> Unit) {
        onItemClickListener = listener
    }


}