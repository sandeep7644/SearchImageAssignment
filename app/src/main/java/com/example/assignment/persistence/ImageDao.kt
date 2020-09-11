package com.example.assignment.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment.models.ImageItem

@Dao
interface ImageDao {

    @Insert
    fun insertImageList(imageList: List<ImageItem>)

    @Query("SELECT * FROM ImageItem")
    fun getImageList(): PagingSource<Int, ImageItem>


    @Query("SELECT COUNT(*) FROM ImageItem")
    fun getCount(): Int


    @Query("DELETE FROM ImageItem")
    suspend fun clearImages()
}
