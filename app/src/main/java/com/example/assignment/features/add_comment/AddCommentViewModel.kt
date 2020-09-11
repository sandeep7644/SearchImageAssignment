package com.example.assignment.features.add_comment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.assignment.repositories.CommentRepository

class AddCommentViewModel @ViewModelInject constructor(val commentRepository: CommentRepository) : ViewModel() {


    fun getComments(imageId: String) = commentRepository.getComments(imageId)
    fun addComment(imageId: String,commentText : String) {
        commentRepository.addComment(imageId,commentText)

    }

}