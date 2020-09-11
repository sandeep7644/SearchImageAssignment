package com.example.assignment.features.add_comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.assignment.R
import com.example.assignment.databinding.RecyclerviewItemCommentBinding
import com.example.assignment.features.common.DataBindingListAdapter
import com.example.assignment.models.CommentItem


class CommentListRecyclerviewAdapter :
    DataBindingListAdapter<CommentItem, RecyclerviewItemCommentBinding>(diffCallback = object :
        DiffUtil.ItemCallback<CommentItem>() {
        override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
            return oldItem == newItem

        }
    }) {
    override fun createBinding(parent: ViewGroup): RecyclerviewItemCommentBinding {
        val binding = DataBindingUtil
            .inflate<RecyclerviewItemCommentBinding>(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_item_comment,
                parent,
                false
            )
        return binding
    }

    private val set = ConstraintSet()
    override fun bind(binding: RecyclerviewItemCommentBinding, item: CommentItem) {
        binding.item = item
    }


}