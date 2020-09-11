package com.example.assignment.repositories

import androidx.paging.*
import com.example.assignment.api.ApiService
import com.example.assignment.models.ImageItem
import com.example.assignment.persistence.ImageDao
import com.example.assignment.persistence.ImageRemotePageKeysDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    val imageDao: ImageDao, val apiService: ApiService, val imageRemotePageKeysDao: ImageRemotePageKeysDao
) {



    @ExperimentalPagingApi
    fun searchImages(searchQuery: String) : Flow<PagingData<ImageItem>> {
        val pagingFactory = { imageDao.getImageList() }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = PostRemoteMediator(
                apiService,
                imageDao,
                imageRemotePageKeysDao,
                searchQuery),
            pagingSourceFactory = pagingFactory

        ).flow

    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}