package com.example.assignment.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.assignment.models.CommentItem
import com.example.assignment.persistence.CommentDao
import com.example.assignment.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepository @Inject constructor(val commentDao: CommentDao) {
    fun getComments(imageId: String): Flow<PagingData<CommentItem>> {
        return Pager(
            config = PagingConfig(pageSize = LOCAL_PAGE_SIZE)
        ) {
            commentDao.getCommentsList(imageId)
        }.flow
    }

    fun addComment(imageId: String, commentText: String) {
        val current_time_millis = System.currentTimeMillis()
        val date_time_formatted = DateUtils.getFormattedDateTime(current_time_millis)
        commentDao.addComment(
            CommentItem(
                date_time = date_time_formatted,
                date_time_in_millis = System.currentTimeMillis(),
                image_id = imageId,
                comment_text = commentText
            )
        )
    }

    companion object {
        private const val LOCAL_PAGE_SIZE = 10
    }

}