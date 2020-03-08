package com.androidtest.management

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.myapp.databinding.ViewholderRectPinkBinding

class ViewHolderPink(binding: ViewholderRectPinkBinding) : RecyclerView.ViewHolder(binding.root) {

    var viewholderEmptyBinding: ViewholderRectPinkBinding?

    init {
        viewholderEmptyBinding = DataBindingUtil.bind(itemView)
    }

    fun bind() {
        viewholderEmptyBinding!!.executePendingBindings()
    }

}