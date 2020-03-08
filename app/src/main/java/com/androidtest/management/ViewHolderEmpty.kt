package com.androidtest.management

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.myapp.databinding.ViewholderEmptyBinding

class ViewHolderEmpty(binding: ViewholderEmptyBinding) : RecyclerView.ViewHolder(binding.root) {

    var viewholderEmptyBinding: ViewholderEmptyBinding?

    init {
        viewholderEmptyBinding = DataBindingUtil.bind(itemView)
    }

    fun bind() {
        viewholderEmptyBinding!!.executePendingBindings()
    }

}