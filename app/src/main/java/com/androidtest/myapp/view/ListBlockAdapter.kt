package com.androidtest.myapp.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.androidtest.management.*
import com.androidtest.myapp.R
import com.androidtest.myapp.databinding.ViewholderEmptyBinding
import com.androidtest.myapp.databinding.ViewholderRectBlueBinding
import com.androidtest.myapp.databinding.ViewholderRectPinkBinding
import com.androidtest.myapp.databinding.ViewholderRectPurpleBinding
import java.lang.Exception

class ListBlockAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_EMPTY = 0
    private val VIEW_ITEM_PINK = 1
    private val VIEW_ITEM_BLUE = 2
    private val VIEW_ITEM_PURPLE = 3

    private lateinit var mContext: Context
    private lateinit var mListener: OnItemListener
    private lateinit var mListData: ArrayList<String>

    interface OnItemListener {
        fun onLastBlock()
    }

    constructor(context: Context, listener: OnItemListener) : this() {
        this.mContext = context
        this.mListener = listener
        mListData = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM_PINK -> {
                val binding = DataBindingUtil.inflate<ViewholderRectPinkBinding>(LayoutInflater.from(parent.context), R.layout.viewholder_rect_pink, parent,false)
                ViewHolderPink(binding)
            }
            VIEW_ITEM_BLUE -> {
                val binding = DataBindingUtil.inflate<ViewholderRectBlueBinding>(LayoutInflater.from(parent.context), R.layout.viewholder_rect_blue, parent,false)
                ViewHolderBlue(binding)
            }
            VIEW_ITEM_PURPLE -> {
                val binding = DataBindingUtil.inflate<ViewholderRectPurpleBinding>(LayoutInflater.from(parent.context), R.layout.viewholder_rect_purple, parent,false)
                ViewHolderPurple(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewholderEmptyBinding>(LayoutInflater.from(parent.context), R.layout.viewholder_empty, parent,false)
                ViewHolderEmpty(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return mListData.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderPink) {
            holder.bind()
        } else if (holder is ViewHolderBlue) {
            holder.bind()
        } else if (holder is ViewHolderPurple) {
            holder.bind()
        } else if (holder is ViewHolderEmpty) {
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_EMPTY
        }
        if (mListData[position - 1] == MainConfig.COLOR.PINK) {
            return VIEW_ITEM_PINK
        }
        if (mListData[position - 1] == MainConfig.COLOR.BLUE) {
            return VIEW_ITEM_BLUE
        }
        return VIEW_ITEM_PURPLE
    }

    fun setDataItem(listData: ArrayList<String>) {
        mListData = listData
        notifyDataSetChanged()
    }

    fun removeBlock() {
        if (mListData.size > 0) {
            try {
                if (mListData[1] == MainConfig.COLOR.PURPLE) {
                    mListener.onLastBlock()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            notifyItemRemoved(0)
            mListData.removeAt(0)
        }
    }

    fun isCurrentColor(): String {
        if (mListData.size > 0) {
            return mListData[0]
        }
        return ""
    }

}