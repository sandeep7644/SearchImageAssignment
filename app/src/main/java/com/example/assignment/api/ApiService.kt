package com.example.assignment.api

import com.example.assignment.models.ImagesListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/gallery/search/{sort}/{page}")
     suspend fun fetchImages(
        @Path("page") page: String,
        @Query("q") query : String
    ): ImagesListResponse

}