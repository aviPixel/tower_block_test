package com.androidtest.management

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.myapp.databinding.ViewholderRectPurpleBinding

class ViewHolderPurple(binding: ViewholderRectPurpleBinding) : RecyclerView.ViewHolder(binding.root) {

    var viewholderEmptyBinding: ViewholderRectPurpleBinding?

    init {
        viewholderEmptyBinding = DataBindingUtil.bind(itemView)
    }

    fun bind() {
        viewholderEmptyBinding!!.executePendingBindings()
    }

}