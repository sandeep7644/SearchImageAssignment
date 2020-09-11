package com.example.assignment.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.models.CommentItem


@Dao
interface CommentDao {

    @Insert
    fun addComment(commentItem: CommentItem)


    @Query("SELECT * FROM CommentItem where image_id = :imageId")
    fun getCommentsList(imageId: String): PagingSource<Int,CommentItem>
}