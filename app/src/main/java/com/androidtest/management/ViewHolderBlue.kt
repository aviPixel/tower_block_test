package com.androidtest.management

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.myapp.databinding.ViewholderRectBlueBinding

class ViewHolderBlue(binding: ViewholderRectBlueBinding) : RecyclerView.ViewHolder(binding.root) {

    var viewholderEmptyBinding: ViewholderRectBlueBinding?

    init {
        viewholderEmptyBinding = DataBindingUtil.bind(itemView)
    }

    fun bind() {
        viewholderEmptyBinding!!.executePendingBindings()
    }

}