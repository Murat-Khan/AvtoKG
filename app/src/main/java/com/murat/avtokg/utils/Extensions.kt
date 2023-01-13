package com.murat.avtokg.utils


import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView

fun RoundedImageView.loadImage(url : String?){
    Glide.with(this.context).load(url).into(this)
}