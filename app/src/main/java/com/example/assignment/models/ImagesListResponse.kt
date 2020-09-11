package com.example.assignment.models

import com.google.gson.annotations.SerializedName

data class ImagesListResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("data")
	val data: List<ImageItem>
)