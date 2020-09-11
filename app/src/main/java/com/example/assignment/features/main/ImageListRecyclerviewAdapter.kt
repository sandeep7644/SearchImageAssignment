package com.example.assignment.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.assignment.R
import com.example.assignment.databinding.RecyclerviewImageItemBinding
import com.example.assignment.features.common.DataBindingListAdapter
import com.example.assignment.models.ImageItem


class ImageListRecyclerviewAdapter(val clickListener : (ImageItem) -> Any):
    DataBindingListAdapter<ImageItem, RecyclerviewImageItemBinding>(diffCallback = object :
        DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem

        }
    }) {
    override fun createBinding(parent: ViewGroup): RecyclerviewImageItemBinding {
        val binding = DataBindingUtil
            .inflate<RecyclerviewImageItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_image_item,
                parent,
                false
            )
        return binding
    }


    private val set = ConstraintSet()
    override fun bind(binding: RecyclerviewImageItemBinding, item: ImageItem) {
        val ratio =String.format("%d:%d", item.imageWidth,item.imageHeight)
        set.clone(binding.constraintLayout)
        set.setDimensionRatio(binding.imageView.id, ratio)
        set.applyTo(binding.constraintLayout)
        binding.item = item
        binding.root.setOnClickListener{
            clickListener.invoke(item)
        }
    }


}