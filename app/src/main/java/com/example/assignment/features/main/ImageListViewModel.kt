package com.example.assignment.features.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment.models.ImageItem
import com.example.assignment.repositories.ImagesRepository
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class ImageListViewModel @ViewModelInject constructor(val imagesRepository: ImagesRepository) :
    ViewModel() {

    var old: Flow<PagingData<ImageItem>>? = null

    fun searchImages(
        swipe_refresh: Boolean = false,
        searchQuery: String
    ): Flow<PagingData<ImageItem>>? {
        if (!swipe_refresh && old != null)
            return old
        old = imagesRepository.searchImages(searchQuery).cachedIn(viewModelScope)
        return old

    }


    init {
        val livedata = MutableLiveData<String>()
        livedata.value = "something"
        Transformations.switchMap(livedata, {
            livedata
        })
    }


}