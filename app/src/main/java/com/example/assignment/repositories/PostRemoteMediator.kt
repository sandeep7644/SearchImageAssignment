package com.example.assignment.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.assignment.api.ApiService
import com.example.assignment.error_handling.EmptyListException
import com.example.assignment.models.ImageItem
import com.example.assignment.persistence.ImageDao
import com.example.assignment.models.ImageRemotePageKeys
import com.example.assignment.persistence.ImageRemotePageKeysDao
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.lang.Exception

private const val POST_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class PostRemoteMediator(
    val apiService: ApiService,
    val imageDao: ImageDao,
    val imageRemotePageKeysDao: ImageRemotePageKeysDao,
    val searchQuery: String
) : RemoteMediator<Int, ImageItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageItem>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: POST_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
//                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }


        try {
            val imagesResponse = apiService.fetchImages(page.toString(), searchQuery)
            if (imagesResponse.success) {
                val imagesList = imagesResponse.data
                val endOfPaginationReached = imagesList.isEmpty()
                if (page == 1 && endOfPaginationReached) {
                    return MediatorResult.Error(EmptyListException("No data found"))
                }
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    imageRemotePageKeysDao.clearRemoteKeys()
                    imageDao.clearImages()
                }
                val prevKey = if (page == POST_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = imagesList.map {
                    ImageRemotePageKeys(
                        repoId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                imageRemotePageKeysDao.insertAll(keys)
                imageDao.insertImageList(imagesList.map {
                    var imageUrl = ""
                    if (it.images?.get(0)?.type.equals("image/jpeg")) {
                        imageUrl = it.images?.get(0)?.link.toString()
                    }
                    it.link = imageUrl
                    it.imageWidth = it.images?.get(0)?.width
                    it.imageHeight = it.images?.get(0)?.height
                    it
                })
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                if (imagesResponse.status == 403) {
                    return MediatorResult.Error(Exception("Unauthorized Access!! Please Set Client ID in Local.properties"))

                }
                return MediatorResult.Error(Exception("An Error Occurred"))
            }

        } catch (exception: IOException) {
            if (imageDao.getCount() > 0) {
                return MediatorResult.Success(endOfPaginationReached = true)

            }
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            if (imageDao.getCount() > 0) {
                return MediatorResult.Success(endOfPaginationReached = true)

            }

            return MediatorResult.Error(exception)
        }


    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageItem>): ImageRemotePageKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                print("asdsadasdasda")
                imageRemotePageKeysDao.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageItem>): ImageRemotePageKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                imageRemotePageKeysDao.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ImageItem>
    ): ImageRemotePageKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                imageRemotePageKeysDao.remoteKeysRepoId(id)
            }
        }
    }

}