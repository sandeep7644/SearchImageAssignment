package com.example.assignment.features.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<VH : ViewDataBinding> (val viewBinding: VH) : RecyclerView.ViewHolder(viewBinding.root) {
}