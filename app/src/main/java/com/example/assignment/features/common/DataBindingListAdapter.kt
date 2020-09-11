package com.example.assignment.features.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil


abstract class  DataBindingListAdapter<T : Any,VH : ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<T>) : PagingDataAdapter<T, DataBindingViewHolder<VH>>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<VH> {
        val binding =createBinding(parent)
        return DataBindingViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DataBindingViewHolder<VH>, position: Int) {
        getItem(position)?.let { bind(holder.viewBinding, it) }
    }
    protected abstract fun createBinding(parent: ViewGroup): VH

    protected abstract fun bind(binding: VH, item: T)

}